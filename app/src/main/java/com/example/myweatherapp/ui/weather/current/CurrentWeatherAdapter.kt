package com.example.myweatherapp.ui.weather.current


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.data.db.entity.Hour
import com.example.myweatherapp.data.utils.formatTimeToHours
import com.example.myweatherapp.data.utils.roundUpDoubleToString
import com.example.weatherapp.R

class CurrentWeatherAdapter(private val forecast: List<Hour>) : RecyclerView.Adapter<CurrentWeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hour = forecast[position]
        holder.bind(hour)

        if(position != 0) {
            holder.llMain.setBackgroundResource(R.drawable.weather_rv_oval_layout_inactive)
        } else {
            holder.llMain.setBackgroundResource(R.drawable.weather_rv_oval_layout_active)
        }
    }

    override fun getItemCount(): Int {
        return forecast.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivWeatherIcon: ImageView = itemView.findViewById(R.id.rvIvWeatherIcon)
        val tvDegrees: TextView = itemView.findViewById(R.id.rvTvDegrees)
        val tvTextTime: TextView = itemView.findViewById(R.id.tvTextTime)
        val llMain: ConstraintLayout = itemView.findViewById(R.id.llRvRow)

        fun bind(forecast: Hour) {
            // Set the weather icon based on the weather condition
            val weatherIconResourceId = getWeatherIconResourceId(forecast.condition.code)

            ivWeatherIcon.setImageResource(weatherIconResourceId)
            val roundUpDegrees = roundUpDoubleToString(forecast.tempC)
            tvDegrees.text = "$roundUpDegrees°C"
            tvTextTime.text = formatTimeToHours(forecast.time)


        }

         private fun getWeatherIconResourceId(weatherIcon: Int): Int {
            // Map the weather icon string to the corresponding drawable resource ID
            return  when (weatherIcon) {
                1000 -> R.drawable.sunny
                1003 -> R.drawable.cloudy
                1006 -> R.drawable.cloudy
                1009 -> R.drawable.cloudy
                1030 -> R.drawable.cloudy
                1063 -> R.drawable.cludy_rain
                1066 -> R.drawable.cloudy
                1069 -> R.drawable.cloudy
                1072 -> R.drawable.cludy_rain
                1087 -> R.drawable.cloudy
                1135 -> R.drawable.cloudy
                1147 -> R.drawable.cloudy
                1150 -> R.drawable.rain
                1153 -> R.drawable.rain
                1168 -> R.drawable.rain
                1171 -> R.drawable.rain
                1180 -> R.drawable.rain
                1183 -> R.drawable.rain
                1186 -> R.drawable.rain
                1189 -> R.drawable.rain
                1192 -> R.drawable.rain
                1195 -> R.drawable.rain
                1198 -> R.drawable.rain
                1201 -> R.drawable.rain
                1204 -> R.drawable.cloudy
                1207 -> R.drawable.cloudy
                1210 -> R.drawable.cloudy
                1213 -> R.drawable.cloudy
                1216 -> R.drawable.cloudy
                1219 -> R.drawable.cloudy
                1240 -> R.drawable.rain
                1243 -> R.drawable.rain
                1246 -> R.drawable.rain
                else -> R.drawable.sunny
            }

        }
    }
}