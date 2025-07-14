package com.example.rickandmortytestapp.features.search.data.remote

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LoggerInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("Request Chain Log", request.url.toString())

        return chain.proceed(request)
    }
}