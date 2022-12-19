package com.lee.foodiadmin.ui.activity.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lee.foodiadmin.R
import com.lee.foodiadmin.common.FooDiAdminApplication
import com.lee.foodiadmin.common.PAGE_ONE
import com.lee.foodiadmin.common.Utils
import com.lee.foodiadmin.data.repository.FooDiRepository
import com.lee.foodiadmin.databinding.ActivitySearchBinding
import com.lee.foodiadmin.factory.FoodiFactory
import com.lee.foodiadmin.ui.activity.search.viewmodel.SearchActivityViewModel
import com.lee.foodiadmin.ui.adapter.SearchFoodRecyclerViewAdapter

class SearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding
    private lateinit var mViewModel : SearchActivityViewModel
    private lateinit var mRecyclerViewAdapter: SearchFoodRecyclerViewAdapter

    private var mCurrentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@SearchActivity , R.layout.activity_search)
        mViewModel = ViewModelProvider(this , FoodiFactory(FooDiRepository.getInstance()))[SearchActivityViewModel::class.java]
        binding.searchViewModel = mViewModel

        addListeners()
        initRecyclerView()
        observeData()
    }

    private fun initRecyclerView() {
        mRecyclerViewAdapter = SearchFoodRecyclerViewAdapter()
        binding.searchFoodRecyclerView.run {
            layoutManager = LinearLayoutManager(FooDiAdminApplication.getInstance())
            adapter = mRecyclerViewAdapter
        }
    }

    private fun addListeners() {
        with(binding){
            inputLayout.setEndIconOnClickListener {
                mViewModel.getSearchFood(PAGE_ONE)
                mCurrentPage = 1
            }
            nextButton.setOnClickListener {
                mViewModel.getSearchFood((++mCurrentPage).toString())
            }
            previousButton.setOnClickListener {
                mViewModel.getSearchFood((--mCurrentPage).toString())
            }
        }
    }

    private fun observeData() {
        with(mViewModel){
            searchFoodList.observe(this@SearchActivity){
                mRecyclerViewAdapter.setList(it)
            }
            errorMessage.observe(this@SearchActivity){
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
}