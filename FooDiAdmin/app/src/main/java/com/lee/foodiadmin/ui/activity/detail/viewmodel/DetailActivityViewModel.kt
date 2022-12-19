package com.lee.foodiadmin.ui.activity.detail.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lee.foodiadmin.common.NOT_AVAILABLE
import com.lee.foodiadmin.data.model.UpdateFoodData
import com.lee.foodiadmin.data.repository.FooDiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

private const val TAG = "DetailActivityViewModel"

class DetailActivityViewModel(private val repository: FooDiRepository) : ViewModel() {
    val foodName = MutableLiveData<String>(NOT_AVAILABLE)
    val calorie = MutableLiveData<String>(NOT_AVAILABLE)
    val carbohydrate = MutableLiveData<String>(NOT_AVAILABLE)
    val protein = MutableLiveData<String>(NOT_AVAILABLE)
    val fat = MutableLiveData<String>(NOT_AVAILABLE)
    val sugar = MutableLiveData<String>(NOT_AVAILABLE)
    val salt = MutableLiveData<String>(NOT_AVAILABLE)
    val cholesterol = MutableLiveData<String>(NOT_AVAILABLE)
    val saturatedFat = MutableLiveData<String>(NOT_AVAILABLE)
    val transFat = MutableLiveData<String>(NOT_AVAILABLE)
    val company = MutableLiveData<String>(NOT_AVAILABLE)
    val servingSize = MutableLiveData<String>(NOT_AVAILABLE)

    val toastMessage = MutableLiveData<String>()
    val isFinishActivity = MutableLiveData(false)

    fun updateFoodData(id : Int) {
        val updateFoodData = UpdateFoodData(
            foodName.value!! ,
            servingSize.value!! ,
            calorie.value!! ,
            carbohydrate.value!! ,
            protein.value!! ,
            fat.value!! ,
            sugar.value!! ,
            salt.value!! ,
            cholesterol.value!! ,
            saturatedFat.value!! ,
            transFat.value!! ,
            company.value!!
        )
        Log.d(TAG, "updateFoodData: $updateFoodData")
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = repository.updateFoodData(id , updateFoodData)
                if(response.isSuccessful){
                    CoroutineScope(Dispatchers.Main).launch{
                        toastMessage.value = "성공적으로 수정했습니다."
                        isFinishActivity.value = true
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch{
                        toastMessage.value = "수정에 실패하였습니다."
                        isFinishActivity.value = false
                    }
                }
            } catch (socketTimeoutException : SocketTimeoutException){
                CoroutineScope(Dispatchers.Main).launch {
                    toastMessage.value = "서버와 통신 제한시간이 지났습니다. 다시 시도하세요."
                    isFinishActivity.value = false
                }
            }
        }
    }


}