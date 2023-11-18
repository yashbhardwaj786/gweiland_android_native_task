package com.yashgweiland.nativeandroidtask.domain

import com.yashgweiland.nativeandroidtask.data.CryptoInfoResponse
import com.yashgweiland.nativeandroidtask.data.model.Result
import com.yashgweiland.nativeandroidtask.data.ResultData
import com.yashgweiland.nativeandroidtask.data.repository.MainRepository
import timber.log.Timber

class FetchCryptoUseCase(
    private val mainRepository: MainRepository,
) {
    suspend fun getLatestCryptoListing( limit: Int, sort: String, sortBy: String): Result<ResultData> {
        var resultData: ResultData? = null
        var exception: Exception? = null
        when (val result = mainRepository.getLatestCryptoListing(limit, sort, sortBy)) {
            is Result.Success -> {
                resultData = result.data
            }
            is Result.Error -> {
                exception = result.exception
                Timber.d(result.exception.stackTraceToString())
            }
            else -> {
                Timber.d("Something went wrong")
            }
        }
        return if(resultData != null){
            Result.Success(resultData)
        }else {
            exception?.let { ex ->
                Result.Error(ex)
            } ?: run {
                Result.Error(Exception("No Result Found"))
            }
        }
    }

    suspend fun getCryptoInfo( slug: String): Result<CryptoInfoResponse> {
        var cryptoInfoResponse: CryptoInfoResponse? = null
        var exception: Exception? = null
        when (val result = mainRepository.getCryptoInfo(slug)) {
            is Result.Success -> {
                cryptoInfoResponse = result.data
            }
            is Result.Error -> {
                exception = result.exception
                Timber.d(result.exception.stackTraceToString())
            }
            else -> {
                Timber.d("Something went wrong")
            }
        }
        return if(cryptoInfoResponse != null){
            Result.Success(cryptoInfoResponse)
        }else {
            exception?.let { ex ->
                Result.Error(ex)
            } ?: run {
                Result.Error(Exception("No Result Found"))
            }
        }
    }
}