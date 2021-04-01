package com.example.parjanya.reusable

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object Utils {


    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
        }
        return false
    }

    fun convertUnixTimeStampToHumanDate(unixTimeStamp: Long?): String{
        return when (unixTimeStamp) {
            null -> {
                ""
            }
            0L -> {
                ""
            }
            else -> {
                val correctedInput = unixTimeStamp * 1000
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = correctedInput
                calendar.timeZone = TimeZone.getTimeZone("Asia/Kolkata")

                val formatter = SimpleDateFormat("dd MMMM", Locale.ENGLISH)
                formatter.format(calendar.time)
            }
        }
    }

    fun appendDegreeCelsiusToValue(value: Double?): String {
        return if (value == null){
            "0 \u2103"
        }else{
            "$value \u2103"
        }
    }

}