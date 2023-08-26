package com.itachi.languagetranslator.helper.retrofit

import com.itachi.languagetranslator.helper.retrofit.models.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @Author: SOHAIB AHMED
 * @Date: 26,August,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://linkedin.com/in/epegasus
 */

interface ClientApi {

    @GET("translate_a/single?client=gtx&dt=t")
    suspend fun translate(@Query("q") query: String, @Query("sl") sourceLanguage: String, @Query("tl") targetLanguage: String): Response<ApiResponse>
    // You might want to use a more specific response type based on the response structure
}