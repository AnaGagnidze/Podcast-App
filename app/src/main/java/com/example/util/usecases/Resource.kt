package com.example.util.usecases

data class Resource<out T>(val status: Status, val data: T?, val errorMessage: String?, val loading:Boolean = false){

    companion object{
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS,data, null)
        }

        fun <T> error(data :T?,msg: String): Resource<T> {
            return Resource(Status.ERROR,data, msg)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null, true)
        }

    }


}
enum class Status {
    SUCCESS, ERROR, LOADING
}
