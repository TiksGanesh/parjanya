package com.example.parjanya.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parjanya.R
import com.example.parjanya.model.DailyWeatherModel

class DailyWeatherAdapter: RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherViewHolder>() {

    private val listOfDailyWeather = mutableListOf<DailyWeatherModel>()

    inner class DailyWeatherViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val date: TextView = item.findViewById(R.id.dateTextView)
        private val minTemp:TextView =  item.findViewById(R.id.minTempTextView)
        private val maxTemp:TextView = item.findViewById(R.id.maxTempTextView)

        fun bindData(dailyWeatherModel: DailyWeatherModel) {
            date.text = dailyWeatherModel.date
            minTemp.text = dailyWeatherModel.minTemp
            maxTemp.text = dailyWeatherModel.maxTemp
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_weather_data, parent, false)
        return DailyWeatherViewHolder(item)
    }

    override fun getItemCount(): Int = listOfDailyWeather.size

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        holder.bindData(listOfDailyWeather[position])
    }

    fun updateData(data: List<DailyWeatherModel>){

        if (data.isNotEmpty()){
            listOfDailyWeather.clear().also {
                listOfDailyWeather.addAll(data)
                notifyDataSetChanged()
            }
        }
    }
}