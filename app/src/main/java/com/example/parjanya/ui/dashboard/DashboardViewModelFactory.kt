package com.example.parjanya.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parjanya.network.RequestService

class DashboardViewModelFactory(private val dashboardUseCase: DashboardUseCase): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DashboardViewModel(dashboardUseCase) as T
    }
}