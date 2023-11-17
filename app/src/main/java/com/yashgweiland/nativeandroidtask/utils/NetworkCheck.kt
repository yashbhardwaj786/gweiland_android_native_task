package com.yashgweiland.nativeandroidtask.utils

import android.content.Context
import android.net.ConnectivityManager
import com.yashgweiland.nativeandroidtask.MyApplication

fun checkInternet(): Boolean {
    val cm = MyApplication.getInstance()
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnected
}