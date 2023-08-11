package com.decode.foodhub.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decode.foodhub.base.BaseRepository
import com.decode.foodhub.models.CategoryResponse
import com.decode.foodhub.models.DetailMealResponse
import com.decode.foodhub.models.MealX
import com.decode.foodhub.models.RandomMeals
import com.decode.foodhub.utils.Constants.CATEGORY_NAME
import com.decode.foodhub.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val baseRepository: BaseRepository): ViewModel() {

    private var _randomMeal = MutableStateFlow<NetworkResult<RandomMeals>>(NetworkResult.Empty)
    val randomMeal = _randomMeal.asStateFlow()

    private var _categories = MutableStateFlow<NetworkResult<CategoryResponse>>(NetworkResult.Empty)
    val categories = _categories.asStateFlow()

    private var _meal = MutableStateFlow<NetworkResult<RandomMeals>>(NetworkResult.Empty)
    val meal = _meal.asStateFlow()

    private var _mealDetail = MutableStateFlow<NetworkResult<DetailMealResponse>>(NetworkResult.Empty)
    val mealDetail = _mealDetail.asStateFlow()

    private var _mealSearch = MutableStateFlow<NetworkResult<DetailMealResponse>>(NetworkResult.Empty)
    val mealSearch = _mealSearch.asStateFlow()

    private var _favMeals = MutableStateFlow<List<MealX>>(mutableListOf())
    val favMeals = _favMeals.asStateFlow()


    //Retrofit
    fun getrandomMeals() = viewModelScope.launch {
        baseRepository.getRandomMeals(CATEGORY_NAME).collect {
            _randomMeal.value = it
        }
    }

    fun getCategory() = viewModelScope.launch {
        baseRepository.getCategory().collect {
            _categories.value = it
        }
    }

    fun getMeals(categoryName: String) = viewModelScope.launch {
        baseRepository.getRandomMeals(categoryName).collect {
            _meal.value = it
        }
    }

    fun getMealDetail(mealId: String) = viewModelScope.launch{
        baseRepository.getMeal(mealId).collect {
            _mealDetail.value = it
        }
    }

    fun searchMeal(firstLetter: String) = viewModelScope.launch {
        baseRepository.searchMeal(firstLetter).collect {
            _mealSearch.value = it
        }
    }

    //Room
    fun insertMeal(mealX: MealX) = viewModelScope.launch {
        baseRepository.insertMeal(mealX)
        getAllMeals()
    }

    fun deleteMeal(mealX: MealX) = viewModelScope.launch {
        baseRepository.deleteMeal(mealX)
        getAllMeals()
    }

    fun getAllMeals() = viewModelScope.launch{
        baseRepository.getAllFavMeals().collect {
            _favMeals.value = it
        }
    }

}