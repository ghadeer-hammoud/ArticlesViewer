package com.ghadeer.articlesviewer.network

import com.ghadeer.articlesviewer.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiClient @Inject constructor() {
    fun getClient(): ApiInterface {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(AuthInterceptor(Constants.API_KEY))
                    .build()
            )
            .build()
            .create(ApiInterface::class.java)
    }
}