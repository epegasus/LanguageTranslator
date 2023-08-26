package com.itachi.languagetranslator.helper.responses

import com.itachi.languagetranslator.helper.enums.ResultType


/**
 * @Author: SOHAIB AHMED
 * @Date: 26,August,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://linkedin.com/in/epegasus
 */

data class TranslateResponse(
    val resultType: ResultType,
    val output: String,
    val exceptionMessage: String? = null
)