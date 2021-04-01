package com.example.parjanya.di

import com.example.parjanya.ui.dashboard.DashboardUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single {
        DashboardUseCase(get())
    }
}