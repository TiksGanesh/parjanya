package com.example.parjanya.di

import com.example.parjanya.network.RequestService
import org.koin.dsl.module

val appModule = module {
    single {
        RequestService(get())
    }
}