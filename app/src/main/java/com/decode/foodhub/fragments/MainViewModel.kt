package com.decode.foodhub.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decode.foodhub.base.BaseRepository
import com.decode.foodhub.models.CategoryResponse
import com.decode.foodhub.models.RandomMeals
import com.decode.foodhub.utils.Constants.CATEGORY_NAME
import com.decode.foodhub.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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

}