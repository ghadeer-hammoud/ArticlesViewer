package com.ghadeer.articlesviewer.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var url = request.url()
        url = url.newBuilder()
            .addQueryParameter("api-key", apiKey)
            .build()

        request = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}