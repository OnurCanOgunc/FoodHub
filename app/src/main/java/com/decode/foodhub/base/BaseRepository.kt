package com.decode.foodhub.base

import com.decode.foodhub.data.network.MealApiService
import com.decode.foodhub.utils.result
import javax.inject.Inject

class BaseRepository @Inject constructor(private val apiService: MealApiService) {

    fun getRandomMeals(categoryName:String) = result {
        apiService.getRandomMeals(categoryName)
    }

    fun getMeal(mealId:String) = result {
        apiService.getMeal(mealId)
    }

    fun getCategory() = result {
        apiService.getCategory()
    }

}