package com.example.util.usecases

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


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