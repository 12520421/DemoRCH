package com.atg.demorch

import android.app.Application
import com.atg.demorch.di.networkModule
import com.atg.demorch.di.repositoriesModule
import com.atg.demorch.di.serviceModule
import com.atg.demorch.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModules,
                    serviceModule,
                    networkModule,
                    repositoriesModule
                )
            )
        }
    }
}