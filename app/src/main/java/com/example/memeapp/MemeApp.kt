package com.example.memeapp

import android.app.Application
import com.example.remote_datasource.di.ServiceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MemeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@MemeApp)
            modules(ServiceModule.serviceModule)
        }
    }
}