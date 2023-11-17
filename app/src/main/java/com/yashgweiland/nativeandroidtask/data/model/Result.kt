package com.yashgweiland.nativeandroidtask.data.model

/**
 *
 * [Result] a generic wrapper for all type of results.
 *
 */

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}