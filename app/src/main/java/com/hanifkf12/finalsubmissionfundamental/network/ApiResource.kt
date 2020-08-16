package com.hanifkf12.finalsubmissionfundamental.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiResource {
    private const val token = "124c7fb159b2e4f05aa7099e9f0b7d0c2f700532"
    private fun createInterceptor() : Interceptor{
        return Interceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.header("Authorization", "token $token")
            return@Interceptor chain.proceed(builder.build())
        }
    }
    fun create(): ApiInterface {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(logging)
        clientBuilder.addInterceptor(createInterceptor())
        val client = clientBuilder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiInterface::class.java)

    }
}