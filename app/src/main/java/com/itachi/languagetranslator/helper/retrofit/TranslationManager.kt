package com.itachi.languagetranslator.helper.retrofit

import com.itachi.languagetranslator.helper.interfaces.TranslationListener
import com.itachi.languagetranslator.helper.retrofit.RetrofitInstance.clientApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @Author: SOHAIB AHMED
 * @Date: 26,August,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://linkedin.com/in/epegasus
 */

class TranslationManager(private val listener: TranslationListener) {

    private val exceptionalHandler = CoroutineExceptionHandler { _, ex: Throwable ->
        listener.onProgressEnd()
        listener.onError(ex as Exception)
    }

    fun translateText(sourceLanguage: String, targetLanguage: String, query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            listener.onProgressStart()
            val asyncTask = CoroutineScope(Dispatchers.IO + exceptionalHandler).async {
                clientApi.translate(sourceLanguage, targetLanguage, query)
            }
            try {
                val apiResponse = asyncTask.await()
                listener.onProgressEnd()
                when (apiResponse.isSuccessful) {
                    true -> {
                        (((apiResponse.body()?.get(0) as? List<*>)?.get(0) as? List<*>)?.get(0))?.let {
                            listener.onSuccess(it.toString())
                        } ?: kotlin.run {
                            val ex = NullPointerException("Invalid api response")
                            listener.onError(ex)
                        }
                    }

                    false -> {
                        listener.onError(IllegalStateException("Response was not successful. Caused by: ${apiResponse.errorBody()}"))
                    }
                }
            } catch (ex: UnknownHostException) {
                listener.onProgressEnd()
                listener.onError(ex)
            } catch (ex: SocketTimeoutException) {
                listener.onProgressEnd()
                listener.onError(ex)
            }
        }
    }
}
