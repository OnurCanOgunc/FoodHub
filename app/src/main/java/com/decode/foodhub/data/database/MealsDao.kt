package com.decode.foodhub.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decode.foodhub.models.MealX
import kotlinx.coroutines.flow.Flow

@Dao
interface MealsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMeal(mealX: MealX)

    @Delete
    suspend fun deleteMeal(mealX: MealX)

    @Query("Select * from mealSav")
    fun getAllMeals(): Flow<List<MealX>>
}