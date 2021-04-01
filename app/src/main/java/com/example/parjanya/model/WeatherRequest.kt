package com.example.parjanya.model

data class WeatherRequest(val latitude:String, val longitude:String, val exclude:String = "minutely,hourly,alerts")