package com.lee.foodiadmin.data.model

data class FoodResponse(
    val pageNo : Int ,
    val totalCount : Int ,
    val results : MutableList<FoodData>
)