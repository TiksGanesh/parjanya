package com.example.parjanya.ui.dashboard

import com.example.parjanya.model.WeatherRequest
import com.example.parjanya.model.WeatherResponse
import com.example.parjanya.network.RequestService
import com.example.parjanya.network.Result

class DashboardRepository(private val requestService: RequestService) {

    suspend fun fetchWeatherData(weatherRequest: WeatherRequest): Result<WeatherResponse> = requestService.getWeatherData(weatherRequest)
}