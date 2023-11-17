package com.yashgweiland.nativeandroidtask.data.repository
import com.yashgweiland.nativeandroidtask.data.model.Result
import com.yashgweiland.nativeandroidtask.exceptions.NoInternetException
import com.yashgweiland.nativeandroidtask.exceptions.TimeoutException
import com.yashgweiland.nativeandroidtask.utils.checkInternet
import okhttp3.Interceptor
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {
        val result: Result<T> = safeApiResult(call, errorMessage)
        var data: T? = null

        when (result) {
            is Result.Success ->
                data = result.data
            is Result.Error -> {
                Timber.d("DataRepository $errorMessage & Exception - ${result.exception}")
                throw Exception(result.exception)
            }
        }

        return data
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Result<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) return Result.Success(response.body()!!)
            if (response.code() == 400) return Result.Error(Exception("Something went wrong try again later"))
            if (response.code() == 500) return Result.Error(Exception("Something went wrong try again later"))
        } catch (exception: SocketTimeoutException) {
            throw (TimeoutException("Connection Timeout"))
        }

        return Result.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }

    class ConnectVerifierInterceptor : Interceptor {
        private val isNetworkConnected: Boolean
            get() {
                return checkInternet()
            }

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            if (!isNetworkConnected) {
                throw NoInternetException("Please check internet connection.")
            }
            val request = chain.request()
            return chain.proceed(request)
        }
    }
}