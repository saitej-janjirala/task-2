package com.saitejajanjirala.task_2.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Util {
    fun ConnectivityManager.isInternetAvailable():Boolean{
        val network = this.activeNetwork ?: return false
        val capabilities = this.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}