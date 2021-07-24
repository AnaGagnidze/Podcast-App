package com.example.util.usecases

import retrofit2.HttpException
import javax.inject.Inject


class ResponseHandler @Inject constructor() {

    fun<T> handleException(e: Exception, data: T? = null):Resource<T>{
        return when(e) {
            is HttpException -> Resource.error(data = data,"HttpException")
            else -> Resource.error(data = data, "Unknown error!")
        }
    }

    fun <T> handleSuccess(data: T?):Resource<T>{
        return Resource.success(data)
    }

    fun <T> handleDefaultException(data: T? = null):Resource<T>{
        return Resource.loading(data)
    }
}