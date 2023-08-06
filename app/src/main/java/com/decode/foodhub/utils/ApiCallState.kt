package com.decode.foodhub.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response


fun <T> result(call: suspend () -> Response<T>): Flow<NetworkResult<T>> = flow {
    emit(NetworkResult.Loading)

    val response = call()

    response.let { it1 ->
        if (it1.isSuccessful && it1.body() != null) {
            emit(NetworkResult.Success(it1.body()!!))
        }
        it1.errorBody()?.let {
            emit(NetworkResult.Error(it.string() as T))
        }
    }
}
