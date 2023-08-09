package com.decode.foodhub.base

import com.decode.foodhub.data.database.MealsDao
import com.decode.foodhub.data.network.MealApiService
import com.decode.foodhub.models.MealX
import com.decode.foodhub.utils.result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BaseRepository @Inject constructor(private val apiService: MealApiService,private val mealsDao: MealsDao) {

    fun getRandomMeals(categoryName:String) = result {
        apiService.getRandomMeals(categoryName)
    }

    fun getMeal(mealId:String) = result {
        apiService.getMeal(mealId)
    }

    fun getCategory() = result {
        apiService.getCategory()
    }

    fun searchMeal(firstLetter:String) = result {
        apiService.searchMeal(firstLetter)
    }

    //Room
    suspend fun insertMeal(mealX: MealX) {
        mealsDao.updateMeal(mealX)
    }

    suspend fun deleteMeal(mealX: MealX) {
        mealsDao.deleteMeal(mealX)
    }

    fun getAllFavMeals(): Flow<List<MealX>> {
        return mealsDao.getAllMeals()
    }
}