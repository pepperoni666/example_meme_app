package com.example.memeapp

import android.app.Application
import com.example.remote_datasource.di.ServiceModule
import com.example.usecase.di.UseCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MemeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@MemeApp)
            modules(UseCaseModule.useCaseModule, ServiceModule.serviceModule)
        }
    }
}