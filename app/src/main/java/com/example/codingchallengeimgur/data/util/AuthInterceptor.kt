package com.example.codingchallengeimgur.data.util

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(

) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .header("Authorization", "Client-ID 359a3907068baee")
            .method(request.method, request.body).build()
        return chain.proceed(newRequest)
    }

}