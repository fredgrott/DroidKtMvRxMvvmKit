package com.example.droidktmvrxmvvmkit

import android.app.Application


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //instantiate db
        Dependencies.getDatabase(this)
    }
}
