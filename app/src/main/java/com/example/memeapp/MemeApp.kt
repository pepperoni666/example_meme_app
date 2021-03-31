package com.example.memeapp

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.example.remote_datasource.di.ServiceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MemeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
        startKoin{
            androidLogger()
            androidContext(this@MemeApp)
            modules(ServiceModule.serviceModule)
        }
    }
}