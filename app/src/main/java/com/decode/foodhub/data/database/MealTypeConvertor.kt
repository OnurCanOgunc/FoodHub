package com.decode.foodhub.data.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConvertor {

    @TypeConverter
    fun fromAnyToString(attr: Any?): String {
        return attr?.let {
            it as String?
        } ?: ""
    }

    @TypeConverter
    fun fromStringToAny(attr: String?): Any {
        return attr ?: ""
    }
}
