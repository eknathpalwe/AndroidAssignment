package com.androidassignment.pref

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    val PREFS_FILENAME = "com.androidassignment"
    val RESPOSNE_DATA = "response_data"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var responseData: String?
        get() = prefs.getString(RESPOSNE_DATA, null)
        set(value) = prefs.edit().putString(RESPOSNE_DATA, value).apply()
}