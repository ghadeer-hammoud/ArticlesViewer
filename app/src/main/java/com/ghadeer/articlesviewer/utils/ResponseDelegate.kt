package com.ghadeer.articlesviewer.utils

import com.ghadeer.articlesviewer.data.NetworkResult
import retrofit2.Response

fun <T> responseDelegate(response: Response<T>): NetworkResult<T?> {
    return when (response.code()) {
        200 -> {
            NetworkResult.Success(response.body())
        }
        401 -> {
            NetworkResult.Failure("Unauthorized request.")
        }
        429 -> {
            NetworkResult.Failure("Too many requests. You reached your per minute or per day rate limit.")
        }
        else -> {
            NetworkResult.Failure("Server Response Code: ${response.code()}")
        }
    }
}