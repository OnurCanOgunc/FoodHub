package com.decode.foodhub.utils

sealed class NetworkResult<out T> {
    data class Success<T>(var data: T) : NetworkResult<T>()
    data class Error<E>(var message: E) : NetworkResult<E>()
    object Loading :NetworkResult<Nothing>()
    object Empty : NetworkResult<Nothing>()
}