package com.example.inostudioTask.common

sealed class Resource<T>(val data: T? = null, val message: Int? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(messageId: Int, data: T? = null) : Resource<T>(data, messageId)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
