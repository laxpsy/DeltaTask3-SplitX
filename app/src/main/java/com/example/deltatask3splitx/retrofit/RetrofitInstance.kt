package com.example.deltatask3splitx.retrofit

import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Arrays
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    const val baseUrl = "http://127.0.0.1:3000/"

    fun getInstance(): Retrofit
    {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttp = OkHttpClient.Builder().addInterceptor(interceptor).protocols(listOf(Protocol.HTTP_1_1))
        okHttp.connectTimeout(10, TimeUnit.SECONDS)
        okHttp.readTimeout(10, TimeUnit.SECONDS)
        okHttp.writeTimeout(10, TimeUnit.SECONDS)
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttp.build()).addConverterFactory(GsonConverterFactory.create()).build()
    }
}