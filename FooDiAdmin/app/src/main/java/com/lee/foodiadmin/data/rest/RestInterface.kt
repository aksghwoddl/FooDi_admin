package com.lee.foodiadmin.data.rest

import com.lee.foodiadmin.common.CONNECTION_TIME_OUT
import com.lee.foodiadmin.common.SEARCH_FOOD_TARGET_URL
import com.lee.foodiadmin.data.model.FoodResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RestInterface {
    @GET(SEARCH_FOOD_TARGET_URL)
    suspend fun getSearchFoodList(
        @Query("desc_kor") foodName : String
        , @Query("pageNo") page : String
    ) : Response<FoodResponse>
}

class RestService {
    companion object{
        private lateinit var instance : RestInterface

        fun getInstance() : RestInterface {
            if(!::instance.isInitialized){
                val interceptor = HttpLoggingInterceptor()
                interceptor.level= HttpLoggingInterceptor.Level.BODY

                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(CONNECTION_TIME_OUT , TimeUnit.MILLISECONDS)
                    .build()
                val retrofit = Retrofit.Builder()
                    .baseUrl(SEARCH_FOOD_TARGET_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                instance = retrofit.create(RestInterface::class.java)
            }
            return instance
        }
    }
}