@file:Suppress("NOTHING_TO_INLINE")

package com.qingmei2.architecture.core.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * 判断网络状态是否可用
 */
fun Context.isNetworkAvailable(): Boolean = applicationContext.isNetworkAvailable()

fun Application.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
    return if (connectivityManager is ConnectivityManager) {
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        networkInfo?.isConnected ?: false
    } else false
}