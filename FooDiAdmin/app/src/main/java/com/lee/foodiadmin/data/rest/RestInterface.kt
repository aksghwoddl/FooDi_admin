package com.lee.foodiadmin.data.rest

import com.lee.foodiadmin.common.DELETE_FOOD_TARGET_URL
import com.lee.foodiadmin.common.SEARCH_FOOD_TARGET_URL
import com.lee.foodiadmin.common.UPDATE_FOOD_TARGET_URL
import com.lee.foodiadmin.data.model.FoodResponse
import com.lee.foodiadmin.data.model.UpdateFoodData
import retrofit2.Response
import retrofit2.http.*

interface RestService {
    @GET(SEARCH_FOOD_TARGET_URL)
    suspend fun getSearchFoodList(
        @Query("desc_kor") foodName : String
        , @Query("pageNo") page : String
    ) : Response<FoodResponse>

    @PATCH(UPDATE_FOOD_TARGET_URL)
    suspend fun updateFoodData(
        @Path("food_id") foodID : Int ,
        @Body foodData : UpdateFoodData
    ) : Response<Void>

    @DELETE(DELETE_FOOD_TARGET_URL)
    suspend fun deleteFoodData(
        @Path("food_id") foodID : Int
    ) : Response<Void>
}