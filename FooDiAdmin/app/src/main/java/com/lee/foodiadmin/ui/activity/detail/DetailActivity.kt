package com.lee.foodiadmin.ui.activity.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jakewharton.rxbinding3.view.clicks
import com.lee.foodiadmin.R
import com.lee.foodiadmin.common.EXTRA_SELECTED_FOOD
import com.lee.foodiadmin.common.NOT_AVAILABLE
import com.lee.foodiadmin.common.UPDATE_DATA
import com.lee.foodiadmin.common.Utils
import com.lee.foodiadmin.data.model.FoodData
import com.lee.foodiadmin.databinding.ActivityDetailBinding
import com.lee.foodiadmin.ui.activity.detail.viewmodel.DetailActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

private const val TAG = "DetailActivity"
private const val CANCEL_DOUBLE_CLICK_TIME = 500L

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private val mViewModel : DetailActivityViewModel by viewModels()
    private lateinit var mFoodData: FoodData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@DetailActivity , R.layout.activity_detail)
        binding.detailViewModel = mViewModel
        initIngredient()
        addListeners()
        observeData()
    }

    private fun initIngredient() {
        mFoodData = intent?.getSerializableExtra(EXTRA_SELECTED_FOOD) as FoodData
        with(mViewModel){
            foodName.value = mFoodData.foodName
            calorie.value = mFoodData.calorie.convertRoundToInt()
            carbohydrate.value = mFoodData.carbohydrate.convertRoundToInt()
            protein.value = mFoodData.protein.convertRoundToInt()
            fat.value = mFoodData.fat.convertRoundToInt()
            sugar.value = mFoodData.sugar.convertRoundToInt()
            salt.value = mFoodData.salt.convertRoundToInt()
            cholesterol.value = mFoodData.cholesterol.convertRoundToInt()
            saturatedFat.value = mFoodData.saturatedFat.convertRoundToInt()
            transFat.value = mFoodData.transFat.convertRoundToInt()
            company.value = mFoodData.company
            servingSize.value = mFoodData.servingWeight.convertRoundToInt()
        }
    }

    private fun addListeners() {
        with(binding){
            modifyButton.clicks().throttleFirst(CANCEL_DOUBLE_CLICK_TIME , TimeUnit.MILLISECONDS).subscribe {
                mViewModel.updateFoodData(mFoodData.id)
            }
        }
    }
    private fun observeData(){
        with(mViewModel){
            toastMessage.observe(this@DetailActivity){
                Utils.toastMessage(it)
            }
            isFinishActivity.observe(this@DetailActivity){
                if(it){
                    val intent = Intent(UPDATE_DATA)
                    sendBroadcast(intent)
                    finish()
                } else {
                    Log.d(TAG, "observeData: did not finish activity something wrong")
                }
            }
        }
    }

    private fun String.convertRoundToInt() : String {
        return if(this.isEmpty() || this == NOT_AVAILABLE){
            Log.d(TAG, "convertRoundToInt: string can't convert roundToInt")
            this
        } else {
            this.toDouble().roundToInt().toString()
        }
    }

}