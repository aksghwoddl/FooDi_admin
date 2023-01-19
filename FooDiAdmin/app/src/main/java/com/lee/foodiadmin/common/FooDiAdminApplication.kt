package com.lee.foodiadmin.common

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FooDiAdminApplication : Application() {
    companion object{
        private lateinit var instance : FooDiAdminApplication
        fun getInstance() : FooDiAdminApplication = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}