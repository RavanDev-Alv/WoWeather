package com.aliyev.woweather.common.utils

import okhttp3.ResponseBody
import org.json.JSONObject

fun findExceptionMessage(errorBody: ResponseBody?): String {
    return if (errorBody != null) {
        val errorObj = JSONObject(errorBody.charStream().readText())
        val errorMessage = errorObj.getJSONObject("error").getString("message")
        errorMessage
    } else {
        "Error"
    }
}

fun findExceptionMessageList(errorBody: ResponseBody?): String {
    return if (errorBody != null) {
        val errorObj = JSONObject(errorBody.charStream().readText())
        val errorArray = errorObj.getJSONObject("error").getJSONArray("message")
        val errorMessage = errorArray.getString(0)
        errorMessage
    } else {
        "Error"
    }
}