package com.example.parjanya.di

import com.example.parjanya.ui.dashboard.DashboardViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        DashboardViewModel(get())
    }
}