package com.androidassignment

import android.app.Application
import com.androidassignment.pref.Prefs

class App : Application() {
    companion object {
        var prefs: Prefs? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext);
    }
}