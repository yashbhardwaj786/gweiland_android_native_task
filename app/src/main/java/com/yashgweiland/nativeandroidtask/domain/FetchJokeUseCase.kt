package com.yashgweiland.nativeandroidtask.domain

import com.yashgweiland.nativeandroidtask.data.model.Result
import com.yashgweiland.nativeandroidtask.data.ResultData
import com.yashgweiland.nativeandroidtask.data.repository.MainRepository
import com.yashgweiland.nativeandroidtask.data.repository.SharedPrefRepository
import timber.log.Timber

const val JOKES_COUNT = 10

class FetchJokeUseCase(
    private val mainRepository: MainRepository,
    private val sharedPrefRepository: SharedPrefRepository
) {
    suspend fun x(): Result<ArrayList<ResultData>> {
        var joke: ResultData? = null
        var exception: Exception? = null
        when (val result = mainRepository.fetchJoke()) {
            is Result.Success -> {
                joke = result.data
            }
            is Result.Error -> {
                exception = result.exception
                Timber.d(result.exception.stackTraceToString())
            }
            else -> {
                Timber.d("Something went wrong")
            }
        }

        sharedPrefRepository.fetchJokesFromSharedPref().let {
            joke?.let { joke ->
                it.add(joke)
            }
            while (it.size > JOKES_COUNT) {
                it.removeAt(0)
            }
            if (it.size > 0) {
                sharedPrefRepository.saveJoke(it)
                return Result.Success(it)
            } else {
                return exception?.let { ex ->
                    Result.Error(ex)
                } ?: run {
                    Result.Error(Exception("No Result Found"))
                }
            }
        }
    }
}