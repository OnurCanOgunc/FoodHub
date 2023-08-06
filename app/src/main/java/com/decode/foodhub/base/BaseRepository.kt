package com.decode.foodhub.base

import com.decode.foodhub.data.network.MealApiService
import com.decode.foodhub.utils.Constants.API_KEY
import com.decode.foodhub.utils.Constants.MEALS_LIMIT
import com.decode.foodhub.utils.result
import javax.inject.Inject

class BaseRepository @Inject constructor(private val apiService: MealApiService) {

    fun getRandomMeals() = result {
        apiService.getRandomMeals(API_KEY, MEALS_LIMIT)
    }

}