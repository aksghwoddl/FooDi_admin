package com.lee.foodiadmin.data.repository

import com.lee.foodiadmin.data.rest.RestService

class FooDiRepository {
    companion object{
        private lateinit var instance : FooDiRepository
        fun getInstance() : FooDiRepository {
            if(!::instance.isInitialized){
                instance = FooDiRepository()
            }
            return instance
        }
    }
    suspend fun getSearchFood(foodName : String , page : String) = RestService.getInstance().getSearchFoodList(foodName , page)
}