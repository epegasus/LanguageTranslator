package com.itachi.languagetranslator.helper.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @Author: SOHAIB AHMED
 * @Date: 26,August,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://linkedin.com/in/epegasus
 */

object RetrofitInstance {

    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://translate.googleapis.com/"

    // Add interceptor for logging details of response
    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    private fun getRetrofitInstance(): Retrofit {
        return retrofit ?: kotlin.run {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            retrofit!!
        }
    }

    val clientApi by lazy {
        getRetrofitInstance().create(ClientApi::class.java)
    }
}