package com.example.network

import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("X-ListenAPI-Key","9f13fa3bb41c460da0ba5d9eb0f85941")
        return chain.proceed(builder.build())
    }
}