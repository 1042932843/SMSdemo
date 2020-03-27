package com.readboy.smsdemo

import android.app.Application

class APP : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        var context: APP? = null
            private set
    }
}