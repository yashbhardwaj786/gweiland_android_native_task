package com.yashgweiland.nativeandroidtask.network

import com.yashgweiland.nativeandroidtask.data.ResultData
import com.yashgweiland.nativeandroidtask.data.repository.BaseRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("api?format=json")
    suspend fun getJoke(): Response<ResultData>

    companion object {
        private const val API_ADDRESS = "https://geek-jokes.sameerkumar.website/"

        fun create(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            val networkInterceptor = BaseRepository.ConnectVerifierInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client: OkHttpClient =
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(networkInterceptor)
                    .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(API_ADDRESS)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}