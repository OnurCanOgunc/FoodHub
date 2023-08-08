package com.decode.foodhub.models


import com.google.gson.annotations.SerializedName

data class DetailMealResponse(
    @SerializedName("meals")
    val meals: List<MealX>?
)