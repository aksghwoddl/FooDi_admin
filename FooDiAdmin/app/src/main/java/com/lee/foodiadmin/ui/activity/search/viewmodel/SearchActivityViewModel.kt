package com.lee.foodiadmin.ui.activity.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lee.foodiadmin.data.model.FoodData
import com.lee.foodiadmin.data.repository.FooDiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class SearchActivityViewModel
    @Inject constructor(private val repository: FooDiRepository) : ViewModel() {
    val searchFoodList = MutableLiveData<MutableList<FoodData>>()
    val searchText = MutableLiveData<String>("")
    val toastMessage = MutableLiveData<String>()
    val isProgress = MutableLiveData(false)
    val isNextButtonEnable = MutableLiveData(false)
    val isPreviousButtonEnable = MutableLiveData(false)

    fun getSearchFood(page : String , callFromDelete : Boolean){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                isProgress.postValue(true)
                val response = repository.getSearchFood(searchText.value!! , page)
                var nextButtonEnable = false
                var previousButtonEnable  = false

                if(response.isSuccessful){
                    if(response.body()?.results!!.isEmpty()){
                        if(callFromDelete){
                            CoroutineScope(Dispatchers.Main).launch {
                                searchFoodList.value = response.body()?.results
                                isNextButtonEnable.value = nextButtonEnable
                                isPreviousButtonEnable.value = previousButtonEnable
                                isProgress.value = false
                            }
                        } else {
                            CoroutineScope(Dispatchers.Main).launch {
                                toastMessage.value = "검색 결과가 존재하지 않습니다."
                                searchFoodList.value = response.body()?.results
                                isNextButtonEnable.value = nextButtonEnable
                                isPreviousButtonEnable.value = previousButtonEnable
                                isProgress.value = false
                            }
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
                    toastMessage.postValue("통신에 실패하였습니다.")
                    isProgress.postValue(false)
                }
            }
        } catch (socketTimeoutException :SocketTimeoutException){
            toastMessage.value = "서버와 통신 제한 시간이 지났습니다. 다시 시도해주세요"
        }
    }

    fun deleteFood(id : Int , page : Int){
        isProgress.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = repository.deleteFoodData(id)
                if(response.isSuccessful){
                    CoroutineScope(Dispatchers.Main).launch {
                        toastMessage.value = "성공적으로 삭제하였습니다."
                        getSearchFood(page.toString() , true)
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch{
                        toastMessage.postValue("통신에 실패하였습니다.")
                        isProgress.value = false
                    }
                }
            } catch (socketTimeoutException :SocketTimeoutException){
                CoroutineScope(Dispatchers.Main).launch{
                    toastMessage.value = "서버와 통신 제한 시간이 지났습니다. 다시 시도해주세요"
                    isProgress.value = false
                }
            }
        }
    }
}