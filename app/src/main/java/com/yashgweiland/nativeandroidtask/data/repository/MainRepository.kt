package com.yashgweiland.nativeandroidtask.data.repository

import com.yashgweiland.nativeandroidtask.data.model.Result
import com.yashgweiland.nativeandroidtask.data.ResultData
import com.yashgweiland.nativeandroidtask.exceptions.NoInternetException
import com.yashgweiland.nativeandroidtask.exceptions.TimeoutException
import com.yashgweiland.nativeandroidtask.network.ApiService
import java.lang.Exception

class MainRepository(private val apiService: ApiService) : BaseRepository() {
    suspend fun fetchJoke(): Result<ResultData>? {
        return try {
            val response = safeApiCall(
                call = {
                    apiService.getJoke()
                },
                errorMessage = "Failed to fetch Joke"
            )

            response?.let {
                Result.Success(it)
            }
        } catch (exception: TimeoutException) {
            Result.Error(exception)
        } catch (exception: NoInternetException) {
            Result.Error(exception)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}