package com.androidassignment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Utils {
    /**
     * Check network connectivity
     * return true, if network connection is connected,
     * false, Otherwise
     */
    fun isOnline(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }
}