package com.lee.foodiadmin.data.repository

import com.lee.foodiadmin.data.model.UpdateFoodData
import com.lee.foodiadmin.data.rest.RestService
import javax.inject.Inject

class FooDiRepository @Inject constructor(
    private val restService: RestService
) {
    suspend fun getSearchFood(foodName : String , page : String) = restService.getSearchFoodList(foodName , page)

    suspend fun updateFoodData(id : Int , foodData: UpdateFoodData) = restService.updateFoodData(id , foodData)

    suspend fun deleteFoodData(id : Int) = restService.deleteFoodData(id)
}