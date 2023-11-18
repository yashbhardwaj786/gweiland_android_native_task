package com.yashgweiland.nativeandroidtask.network

import com.yashgweiland.nativeandroidtask.data.ResultData
import com.yashgweiland.nativeandroidtask.data.repository.BaseRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.yashgweiland.nativeandroidtask.BuildConfig
import com.yashgweiland.nativeandroidtask.data.CryptoInfoResponse
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getLatestCryptoListing(@Query("limit") limit: Int, @Query("sort") sort: String, @Query("sort_dir") sortBy: String): Response<ResultData>

    @GET("v2/cryptocurrency/info")
    suspend fun getCryptoCurrencyInfo(@Query("slug") slug: String): Response<CryptoInfoResponse>

    companion object {
        fun create(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            val networkInterceptor = BaseRepository.ConnectVerifierInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client: OkHttpClient =
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .build()

                val request: Request = original.newBuilder()
                    .header("X-CMC_PRO_API_KEY", BuildConfig.apiKey)
                    .url(url)
                    .build()

                chain.proceed(request)
            }.addInterceptor(networkInterceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}