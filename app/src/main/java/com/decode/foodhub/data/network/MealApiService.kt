package com.decode.foodhub.data.network


import com.decode.foodhub.models.CategoryResponse
import com.decode.foodhub.models.RandomMeals
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("filter.php")
    suspend fun getRandomMeals(
        @Query("c") categoryName: String
    ):Response<RandomMeals>

    @GET("categories.php")
    suspend fun getCategory(): Response<CategoryResponse>
}