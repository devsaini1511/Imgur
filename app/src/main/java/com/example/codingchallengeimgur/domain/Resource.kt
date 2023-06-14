package com.example.codingchallengeimgur.domain

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val throwable: Throwable) : Resource<Nothing>()

    override fun toString(): String {
        return when(this) {
            is Failure -> "Failure"
            Loading -> "Loading"
            is Success -> "Success(${this.data})"
        }
    }
}