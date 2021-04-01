package com.example.parjanya.network

/**
 * Class: Result
 * Description: Result to handle response from Server
 */
sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}