package com.example.parjanya.network

import android.content.Context
import com.example.parjanya.core.AppConstant
import com.example.parjanya.model.WeatherRequest
import com.example.parjanya.model.WeatherResponse
import java.io.IOException


class RequestService(context: Context) {

    private val requestInterface by lazy {
        RequestInterface.invoke(context)
    }

    /**
     * Top Level function to avoid writing try catch block to each api function
     */
    private suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> = try {
        call.invoke()
    } catch (e: Exception) {
        Result.Error(IOException(e.localizedMessage, e))
    }

    // -------------------------  API CALL ----------------------------------


    /**
     * API Call with Try Catch handling
     */
    suspend fun getWeatherData(weatherRequest: WeatherRequest):Result<WeatherResponse> = safeApiCall(
        call = {weatherData(weatherRequest)},
        errorMessage = "Error to fetch weather data"
    )

    /**
     * API Call for getting weather data`8530473555
     */
    private suspend fun weatherData(weatherRequest: WeatherRequest) : Result<WeatherResponse>{

        val response = requestInterface.getWeatherDataAsync(
            weatherRequest.latitude,
            weatherRequest.longitude,
            weatherRequest.exclude,
            AppConstant.METRIC,
            AppConstant.APP_ID
        ).await()

        return if (response.isSuccessful
            && null != response.body()
        ) {
            Result.Success(response.body()!!)
        } else {
            Result.Error(IOException(response.message()))
        }
    }
}