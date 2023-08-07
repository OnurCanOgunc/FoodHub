package com.decode.foodhub.models


import com.google.gson.annotations.SerializedName

data class RandomMeals(
    @SerializedName("meals")
    val meals: List<Meal?>?
)