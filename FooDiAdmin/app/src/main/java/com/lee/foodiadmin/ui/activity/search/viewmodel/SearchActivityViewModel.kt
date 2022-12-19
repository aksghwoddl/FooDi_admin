package com.lee.foodiadmin.ui.activity.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lee.foodiadmin.data.model.FoodData
import com.lee.foodiadmin.data.repository.FooDiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class SearchActivityViewModel(private val repository: FooDiRepository) : ViewModel() {
    val searchFoodList = MutableLiveData<MutableList<FoodData>>()
    val searchText = MutableLiveData<String>("")
    val errorMessage = MutableLiveData<String>()
    val isProgress = MutableLiveData(false)
    val isNextButtonEnable = MutableLiveData(false)
    val isPreviousButtonEnable = MutableLiveData(false)

    fun getSearchFood(page : String){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                isProgress.postValue(true)
                val response = repository.getSearchFood(searchText.value!! , page)
                var nextButtonEnable = false
                var previousButtonEnable  = false

                if(response.isSuccessful){
                    if(response.body()?.results!!.isEmpty()){
                        CoroutineScope(Dispatchers.Main).launch {
                            errorMessage.value = "검색 결과가 존재하지 않습니다."
                        }
                    } else {
                        if(response.body()?.totalCount!! > 1 && page.toInt() < response.body()?.totalCount!!){
                            nextButtonEnable = true
                        }
                        if(response.body()?.totalCount!! > 1 && page.toInt() > 1){
                            previousButtonEnable = true
                        }
                        CoroutineScope(Dispatchers.Main).launch {
                            searchFoodList.value = response.body()?.results
                            isNextButtonEnable.value = nextButtonEnable
                            isPreviousButtonEnable.value = previousButtonEnable
                            isProgress.value = false
                        }
                    }
                } else {
                    errorMessage.postValue("통신에 실패하였습니다.")
                    isProgress.value = false
                }
            }
        } catch (socketTimeoutException :SocketTimeoutException){
            errorMessage.value = "서버와 통신 제한 시간이 지났습니다. 다시 시도해주세요"
        }
    }
}