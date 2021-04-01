package com.example.parjanya.di

import com.example.parjanya.ui.dashboard.DashboardRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        DashboardRepository(get())
    }
}