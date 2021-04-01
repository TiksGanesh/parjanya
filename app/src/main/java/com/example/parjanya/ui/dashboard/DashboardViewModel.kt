package com.example.parjanya.ui.dashboard

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parjanya.model.DailyWeatherModel
import com.example.parjanya.model.WeatherRequest
import com.example.parjanya.model.WeatherResponse
import com.example.parjanya.network.Result
import com.example.parjanya.reusable.Utils
import kotlinx.coroutines.launch

class DashboardViewModel(private val dashboardUseCase:DashboardUseCase) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Fetching weather data..."
    }
    val text: LiveData<String> = _text

    var location: Location = Location("")

    private val _apiResponse = MutableLiveData<List<DailyWeatherModel>>()
    val apiResponse = _apiResponse


    fun fetchData(request: WeatherRequest) {

        viewModelScope.launch {

            dashboardUseCase.fetchDataWeatherUseCase(request)
            dashboardUseCase.useCaseResponse.observeForever {
                when(it){
                    is Result.Success -> {
                        val output = convertResponseIntoDailyWeatherModel(it.data)
                        _apiResponse.postValue(output)
                    }

                    is Result.Error -> {
                        Log.d("###",it.exception.localizedMessage?:"")
                        _text.postValue(it.exception.localizedMessage?:"")
                    }
                }
            }

        }
    }


   private fun convertResponseIntoDailyWeatherModel(data: WeatherResponse): List<DailyWeatherModel>{

       val mutableDailyWeatherModel = mutableListOf<DailyWeatherModel>()

       if (data.daily != null){
           data.daily.forEach {
               it?.let { wd ->
                   val model = DailyWeatherModel(
                       Utils.convertUnixTimeStampToHumanDate(wd.dt),
                       "Min: ${Utils.appendDegreeCelsiusToValue(wd.temp?.min)}",
                       "Max: ${Utils.appendDegreeCelsiusToValue(wd.temp?.max)}"
                   )
                   mutableDailyWeatherModel.add(model)
               }
           }
       }

       return mutableDailyWeatherModel.toList()
   }
}