package com.decode.foodhub.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decode.foodhub.models.MealX

@Database(entities = [MealX::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeConvertor::class)
abstract class MealsDatabase : RoomDatabase() {

    abstract fun mealsDao(): MealsDao
}