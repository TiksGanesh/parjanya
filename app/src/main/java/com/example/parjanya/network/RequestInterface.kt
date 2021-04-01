package com.example.parjanya.network

import android.content.Context
import com.example.parjanya.R
import com.example.parjanya.model.WeatherResponse
import com.example.parjanya.reusable.Utils
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Interface: RequestInterface
 * Description: RequestInterface Retrofit Interface to declare all abstract api function
 */
interface RequestInterface {

    @GET("onecall")
    fun getWeatherDataAsync(@Query("lat") lat: String,
                            @Query("lon") lon: String,
                            @Query("exclude") exclude: String,
                            @Query("units") units: String,
                            @Query("appid") appId: String
    ): Deferred<Response<WeatherResponse>>


    companion object {

        /**
         * BASE URL
         */
        private var BASEURL = "https://api.openweathermap.org/data/2.5/"

        /**
         * TIME OUT
         */
        private val REQUEST_TIME_OUT = 120L

        operator fun invoke(context:Context):RequestInterface {

            val okHttpClientBuilder = getOKHttpClientBuilder()
            val okHttpClient = addInterceptorsAndBuild(okHttpClientBuilder, context)
            return Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(RequestInterface::class.java)
        }

        /**
         * Get OK HTTP Client Builder
         */
        private fun getOKHttpClientBuilder(): OkHttpClient.Builder{
            return OkHttpClient.Builder()
                .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        }

        /**
         * Add interceptors to okhttp client
         * 1. Logger
         * 2. Request Header
         * 3. Connectivity Interceptor
         */
        private fun addInterceptorsAndBuild(
            okHttpClientBuilder: OkHttpClient.Builder,
            context: Context
        ): OkHttpClient {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            // --- Logger Interceptor --
            okHttpClientBuilder.addInterceptor(interceptor)

            // -- Connectivity Interceptor --
            okHttpClientBuilder.addInterceptor { chain: Interceptor.Chain? ->
                if (!Utils.isInternetAvailable(context)) {
                    throw NoConnectivityException(context.getString(R.string.no_internet_connection))
                }
                val requestBuilder = chain!!.request().newBuilder()
                chain.proceed(requestBuilder.build())
            }

            // -- Header Interceptor --
            okHttpClientBuilder.addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                val request = requestBuilder.build()
                chain.proceed(request)
            }

            return okHttpClientBuilder.build()
        }
    }


}