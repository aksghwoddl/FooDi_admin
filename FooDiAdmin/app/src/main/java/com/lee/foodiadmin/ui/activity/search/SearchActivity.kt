package com.lee.foodiadmin.ui.activity.search

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu.OnMenuItemClickListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.lee.foodiadmin.R
import com.lee.foodiadmin.common.EXTRA_SELECTED_FOOD
import com.lee.foodiadmin.common.PAGE_ONE
import com.lee.foodiadmin.common.UPDATE_DATA
import com.lee.foodiadmin.common.Utils
import com.lee.foodiadmin.common.manager.CustomLinearLayoutManager
import com.lee.foodiadmin.data.model.FoodData
import com.lee.foodiadmin.databinding.ActivitySearchBinding
import com.lee.foodiadmin.ui.activity.detail.DetailActivity
import com.lee.foodiadmin.ui.activity.search.viewmodel.SearchActivityViewModel
import com.lee.foodiadmin.ui.adapter.SearchFoodRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SearchActivity"

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding
    private val mViewModel : SearchActivityViewModel by viewModels()
    private lateinit var mRecyclerViewAdapter: SearchFoodRecyclerViewAdapter
    private lateinit var mSearchBroadcastReceiver: BroadcastReceiver

    private var mCurrentPage = 0
    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@SearchActivity , R.layout.activity_search)
        binding.searchViewModel = mViewModel
        addListeners()
        initRecyclerView()
        initBroadcastReceiver()
        observeData()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
        if(isUpdate){
            Log.d(TAG, "onResume : data updated!!")
            isUpdate = false
            mViewModel.getSearchFood(mCurrentPage.toString() , false)
        }
    }

    private fun initBroadcastReceiver() {
        mSearchBroadcastReceiver = SearchBroadcastReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(UPDATE_DATA)
        registerReceiver(mSearchBroadcastReceiver , intentFilter)
    }

    private fun initRecyclerView() {
        mRecyclerViewAdapter = SearchFoodRecyclerViewAdapter()
        mRecyclerViewAdapter.setOnItemClickListener(FoodItemClickListener())
        mRecyclerViewAdapter.setOnMenuItemClickListener(MenuItemClickListener())
        binding.searchFoodRecyclerView.run {
            layoutManager = CustomLinearLayoutManager(this@SearchActivity)
            adapter = mRecyclerViewAdapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    private fun addListeners() {
        with(binding){
            inputLayout.setEndIconOnClickListener {
                mViewModel.getSearchFood(PAGE_ONE , false)
                mCurrentPage = 1
            }
            nextButton.setOnClickListener {
                mViewModel.getSearchFood((++mCurrentPage).toString() , false)
            }
            previousButton.setOnClickListener {
                mViewModel.getSearchFood((--mCurrentPage).toString(), false)
            }
        }
    }

    private fun observeData() {
        with(mViewModel){
            searchFoodList.observe(this@SearchActivity){
                mRecyclerViewAdapter.setList(it)
            }
            toastMessage.observe(this@SearchActivity){
                Utils.toastMessage(it)
            }
            isProgress.observe(this@SearchActivity){
                if(it){
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
            isNextButtonEnable.observe(this@SearchActivity){
                binding.nextButton.isEnabled = it
            }
            isPreviousButtonEnable.observe(this@SearchActivity){
                binding.previousButton.isEnabled = it
            }
        }
    }

    private inner class FoodItemClickListener : SearchFoodRecyclerViewAdapter.OnItemClickListener {
        override fun onClick(view: View, model: FoodData, position: Int) {
            super.onClick(view, model, position)
            with(Intent(this@SearchActivity , DetailActivity::class.java)){
                putExtra(EXTRA_SELECTED_FOOD , model)
                startActivity(this)
            }
        }
    }

    private inner class SearchBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent : Intent?) {
            when(intent?.action){
                UPDATE_DATA -> {
                    Log.d(TAG, "onReceive: UPDATE_DATA")
                    isUpdate = true
                }
            }
        }
    }

    private inner class MenuItemClickListener : OnMenuItemClickListener {
        override fun onMenuItemClick(p0: MenuItem?): Boolean {
            mViewModel.deleteFood(mRecyclerViewAdapter.getSelectedData().id , mCurrentPage)
            return true
        }
    }
}