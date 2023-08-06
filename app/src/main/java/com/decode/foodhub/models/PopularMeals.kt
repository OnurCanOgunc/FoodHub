package com.decode.foodhub.models


import com.google.gson.annotations.SerializedName

data class PopularMeals(
    @SerializedName("recipes")
    val recipes: List<Recipe?>?
)