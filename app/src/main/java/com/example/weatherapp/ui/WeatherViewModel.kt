package com.example.weatherapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.WeatherRepository
import data.local.WeatherReport
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class WeatherViewModel : ViewModel() {

    var cityName by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    val weatherResults = mutableStateListOf<WeatherReport>()
    val repository = WeatherRepository()

    fun fetchWeather(apiKey: String) {
        viewModelScope.launch {
            isLoading = true // Turn on the 'loading spinner'
            val report = repository.getWeather(cityName, apiKey)
            weatherResults.add(report)
            isLoading = false // Turn off loading spinner
        }
    }
}