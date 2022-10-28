package com.tsl.elevator

import android.app.Application
import com.tsl.elevator.network.ApiClient
import com.tsl.elevator.network.ApiInterface


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
    }

    companion object {
        var instance: App? = null
    }
}