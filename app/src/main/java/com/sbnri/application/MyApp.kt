package com.sbnri.application

import android.app.Application
import android.content.Context

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        _INSTANCE = this
    }

    companion object {
        lateinit var _INSTANCE: MyApp
    }

    fun getAppContext(): Context {
        return _INSTANCE.applicationContext
    }
}