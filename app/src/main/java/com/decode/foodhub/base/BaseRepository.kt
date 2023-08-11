package com.decode.foodhub.base

import com.decode.foodhub.data.database.MealsDao
import com.decode.foodhub.data.network.MealApiService
import com.decode.foodhub.models.MealX
import com.decode.foodhub.utils.result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BaseRepository @Inject constructor(
    private val apiService: MealApiService,
    private val mealsDao: MealsDao
) {

    fun getRandomMeals(categoryName: String) = result {
        apiService.getRandomMeals(categoryName)
    }

    fun getMeal(mealId: String) = result {
        apiService.getMeal(mealId)
    }

    fun getCategory() = result {
        apiService.getCategory()
    }
/*
    fun searchMeal(firstLetter: String) = result {
        apiService.searchMeal(firstLetter)
    }
*/
    //Room
    suspend fun insertMeal(mealX: MealX) = withContext(Dispatchers.IO) {
        mealsDao.updateMeal(mealX)
    }

    suspend fun deleteMeal(mealX: MealX) = withContext(Dispatchers.IO) {
        mealsDao.deleteMeal(mealX)
    }

    fun getAllFavMeals() = mealsDao.getAllMeals()

}