package com.example.parjanya.core

import android.app.Application
import com.example.parjanya.di.appModule
import com.example.parjanya.di.repositoryModule
import com.example.parjanya.di.useCaseModule
import com.example.parjanya.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ParjanyaApp:Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialise DI

        startKoin {
            androidContext(this@ParjanyaApp)
            modules(listOf(appModule, repositoryModule, useCaseModule, viewModelModule))
        }
    }
}