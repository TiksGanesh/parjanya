package com.example.parjanya.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parjanya.model.WeatherRequest
import com.example.parjanya.model.WeatherResponse
import com.example.parjanya.network.RequestService
import com.example.parjanya.network.Result
import java.lang.Exception

class DashboardUseCase(private val dashboardRepository: DashboardRepository) {



    /**
     * Mutable Live Data
     */
    private val _useCaseResponse = MutableLiveData<Result<WeatherResponse>>()

    /**
     * Live data
     */
    val useCaseResponse: LiveData<Result<WeatherResponse>> = _useCaseResponse

    suspend fun fetchDataWeatherUseCase(weatherRequest: WeatherRequest){

        if (weatherRequest.latitude.isEmpty() || weatherRequest.longitude.isEmpty()){
            _useCaseResponse.postValue(Result.Error(Exception("Location error")))
        }else{
            val response = dashboardRepository.fetchWeatherData(weatherRequest)
            _useCaseResponse.postValue(response)
        }

    }
}