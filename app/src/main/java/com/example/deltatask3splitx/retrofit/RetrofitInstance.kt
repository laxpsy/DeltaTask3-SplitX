package com.example.deltatask3splitx.retrofit

import okhttp3.OkHttp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    const val baseUrl = "http://127.0.0.1:3000/"

    fun getInstance(): Retrofit
    {
        val okHttp = OkHttpClient.Builder()
        okHttp.connectTimeout(20, TimeUnit.SECONDS)
        okHttp.readTimeout(20, TimeUnit.SECONDS)
        okHttp.writeTimeout(20, TimeUnit.SECONDS)
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttp.build()).addConverterFactory(GsonConverterFactory.create()).build()
    }
}