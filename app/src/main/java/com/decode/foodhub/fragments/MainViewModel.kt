package com.decode.foodhub.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decode.foodhub.base.BaseRepository
import com.decode.foodhub.models.CategoryResponse
import com.decode.foodhub.models.RandomMeals
import com.decode.foodhub.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val baseRepository: BaseRepository): ViewModel() {

    private var _randomMeal = MutableStateFlow<NetworkResult<RandomMeals>>(NetworkResult.Empty)
    val randomMeal = _randomMeal.asStateFlow()
    private var _categories = MutableStateFlow<NetworkResult<CategoryResponse>>(NetworkResult.Empty)
    val categories = _categories.asStateFlow()

    fun getRandomMeals() = viewModelScope.launch {
        baseRepository.getRandomMeals().collect {
            _randomMeal.value = it
        }
    }

    fun getCategory() = viewModelScope.launch {
        baseRepository.getCategory().collect {
            _categories.value = it
        }
    }
}