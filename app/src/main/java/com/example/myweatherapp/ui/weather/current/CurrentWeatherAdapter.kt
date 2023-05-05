package com.example.myweatherapp.ui.weather.current

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.databinding.ItemWeatherRowBinding

class CurrentWeatherAdapter(private val items: List<String>):
    RecyclerView.Adapter<CurrentWeatherAdapter.ViewHolder>(){

    class ViewHolder(binding: ItemWeatherRowBinding): RecyclerView.ViewHolder(binding.root){
            val llMain = binding.llRvRow
            val weatherIcon = binding.rvIvWeatherIcon
            val degrees = binding.rvTvDegrees
            val tvTime = binding.tvTextTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWeatherRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}