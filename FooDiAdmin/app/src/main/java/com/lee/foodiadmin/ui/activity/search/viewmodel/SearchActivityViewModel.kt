package com.lee.foodiadmin.ui.activity.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lee.foodiadmin.R
import com.lee.foodiadmin.common.ResourceProvider
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
    @Inject constructor(
        private val repository: FooDiRepository ,
        private val resourceProvider: ResourceProvider
    ) : ViewModel() {
    val searchText = MutableLiveData<String>("")

    private val _searchFoodList = MutableLiveData<MutableList<FoodData>>()
    val searchFoodList : LiveData<MutableList<FoodData>>
    get() = _searchFoodList

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage : LiveData<String>
    get() = _toastMessage

    private val _isProgress = MutableLiveData(false)
    val isProgress : LiveData<Boolean>
    get() = _isProgress

    private val _isNextButtonEnable = MutableLiveData(false)
    val isNextButtonEnable : LiveData<Boolean>
    get() = _isNextButtonEnable

    private val _isPreviousButtonEnable = MutableLiveData(false)
    val isPreviousButtonEnable : LiveData<Boolean>
    get() = _isPreviousButtonEnable

    fun getSearchFood(page : String , callFromDelete : Boolean){
        try{
            viewModelScope.launch {
                _isProgress.value = true
                val response = repository.getSearchFood(searchText.value!! , page)
                var nextButtonEnable = false
                var previousButtonEnable  = false

                if(response.isSuccessful){
                    if(response.body()?.results!!.isEmpty()){
                        if(callFromDelete){
                                _searchFoodList.value = response.body()?.results
                                _isNextButtonEnable.value = nextButtonEnable
                                _isPreviousButtonEnable.value = previousButtonEnable
                                _isProgress.value = false

                        } else {
                                _toastMessage.value = resourceProvider.getString(R.string.empty_list)
                                _searchFoodList.value = response.body()?.results
                                _isNextButtonEnable.value = nextButtonEnable
                                _isPreviousButtonEnable.value = previousButtonEnable
                                _isProgress.value = false
                        }

                    } else {
                        if(response.body()?.totalCount!! > 1 && page.toInt() < response.body()?.totalCount!!){
                            nextButtonEnable = true
                        }
                        if(response.body()?.totalCount!! > 1 && page.toInt() > 1){
                            previousButtonEnable = true
                        }
                            _searchFoodList.value = response.body()?.results
                            _isNextButtonEnable.value = nextButtonEnable
                            _isPreviousButtonEnable.value = previousButtonEnable
                            _isProgress.value = false
                    }
                } else {
                    _toastMessage.value = resourceProvider.getString(R.string.response_fail)
                    _isProgress.value = false
                }
            }
        } catch (socketTimeoutException :SocketTimeoutException){
            _toastMessage.value = resourceProvider.getString(R.string.socket_timeout)
            _isProgress.value = false
        }
    }

    fun deleteFood(id : Int , page : Int){
        _isProgress.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = repository.deleteFoodData(id)
                if(response.isSuccessful){
                    viewModelScope.launch {
                        _toastMessage.value = resourceProvider.getString(R.string.delete_success)
                        getSearchFood(page.toString() , true)
                    }
                } else {
                    viewModelScope.launch{
                        _toastMessage.value = resourceProvider.getString(R.string.response_fail)
                        _isProgress.value = false
                    }
                }
            } catch (socketTimeoutException :SocketTimeoutException){
                viewModelScope.launch{
                    _toastMessage.value = resourceProvider.getString(R.string.socket_timeout)
                    _isProgress.value = false
                }
            }
        }
    }
}