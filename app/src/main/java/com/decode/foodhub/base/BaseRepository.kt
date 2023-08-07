package com.decode.foodhub.base

import com.decode.foodhub.data.network.MealApiService
import com.decode.foodhub.utils.Constants.CATEGORY_NAME
import com.decode.foodhub.utils.result
import javax.inject.Inject

class BaseRepository @Inject constructor(private val apiService: MealApiService) {

    fun getRandomMeals() = result {
        apiService.getRandomMeals(CATEGORY_NAME)
    }

    fun getCategory() = result {
        apiService.getCategory()
    }

}