package com.lee.foodiadmin.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lee.foodiadmin.data.repository.FooDiRepository
import com.lee.foodiadmin.ui.activity.detail.viewmodel.DetailActivityViewModel
import com.lee.foodiadmin.ui.activity.search.viewmodel.SearchActivityViewModel

class FoodiFactory(private val repository: FooDiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchActivityViewModel::class.java)){
            return SearchActivityViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(DetailActivityViewModel::class.java)){
            return DetailActivityViewModel(repository) as T
        } else {
            throw java.lang.IllegalArgumentException("해당 ViewModel을 찾을수 없습니다.")
        }
    }
}