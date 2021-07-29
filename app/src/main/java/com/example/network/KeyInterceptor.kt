package com.example.network

import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("X-ListenAPI-Key","2545ae35c5bf40f39a5fa79e6b361aac")
        return chain.proceed(builder.build())
    }
}