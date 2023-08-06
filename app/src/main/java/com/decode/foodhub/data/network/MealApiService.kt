package com.decode.foodhub.data.network


import com.decode.foodhub.models.PopularMeals
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("recipes/random")
    suspend fun getRandomMeals(
        @Query("apiKey") apiKey:String,
        @Query("number") number:Int
    ):Response<PopularMeals>

}