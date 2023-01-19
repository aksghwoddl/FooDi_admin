package com.lee.foodiadmin.ui.activity.detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lee.foodiadmin.R
import com.lee.foodiadmin.common.NOT_AVAILABLE
import com.lee.foodiadmin.common.ResourceProvider
import com.lee.foodiadmin.data.model.UpdateFoodData
import com.lee.foodiadmin.data.repository.FooDiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

private const val TAG = "DetailActivityViewModel"

@HiltViewModel
class DetailActivityViewModel
    @Inject constructor (
        private val repository: FooDiRepository ,
        private val resourceProvider: ResourceProvider
        ) : ViewModel() {
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

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage : LiveData<String>
    get() = _toastMessage

    private val _isFinishActivity = MutableLiveData(false)
    val isFinishActivity : LiveData<Boolean>
    get() = _isFinishActivity

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

        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = repository.updateFoodData(id , updateFoodData)
                if(response.isSuccessful){
                    viewModelScope.launch{
                        _toastMessage.value = resourceProvider.getString(R.string.modify_success)
                        _isFinishActivity.value = true
                    }
                } else {
                    viewModelScope.launch{
                        _toastMessage.value = resourceProvider.getString(R.string.response_fail)
                        _isFinishActivity.value = false
                    }
                }
            } catch (socketTimeoutException : SocketTimeoutException){
                viewModelScope.launch {
                    _toastMessage.value = resourceProvider.getString(R.string.socket_timeout)
                    _isFinishActivity.value = false
                }
            }
        }
    }


}