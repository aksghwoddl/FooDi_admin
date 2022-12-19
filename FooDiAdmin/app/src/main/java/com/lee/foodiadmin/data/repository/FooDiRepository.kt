package com.lee.foodiadmin.data.repository

import com.lee.foodiadmin.data.model.UpdateFoodData
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

    suspend fun updateFoodData(id : Int , foodData: UpdateFoodData) = RestService.getInstance().updateFoodData(id , foodData)

    suspend fun deleteFoodData(id : Int) = RestService.getInstance().deleteFoodData(id)
}