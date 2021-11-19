package com.example.inostudioTask.common

sealed class Resource{
    data class Success<T>(val data: T? = null)
    data class Error<T>(val message: T?, val data: T? = null)
    data class Loading<T>(val data: T? = null)
}
