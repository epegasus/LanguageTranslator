package com.itachi.languagetranslator.helper.interfaces


/**
 * @Author: SOHAIB AHMED
 * @Date: 26,August,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

interface TranslationListener {
    fun onProgressStart()
    fun onProgressEnd()
    fun onSuccess(text: String)
    fun onError(ex: Exception)
}