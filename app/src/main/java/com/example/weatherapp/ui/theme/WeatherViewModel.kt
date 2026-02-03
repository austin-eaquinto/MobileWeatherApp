package com.example.weatherapp.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class WeatherViewModel : ViewModel() {

    var cityName by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    val weatherResults = mutableStateListOf<WeatherReport>()

    fun fetchWeather(apiKey: String) {
        // Using 'viewModelScope' instead of 'rememberCoroutineScope'
        // To keep the behind-the-scenes work alive even if the screen rotates
        viewModelScope.launch {
            isLoading = true // Turn on the 'loading spinner'
            try {
                val response = RetrofitInstance.api.getWeather(cityName, apiKey)
                val report = WeatherReport(
                    cityName = cityName,
                    temperature = response.main.temperature,
                    condition = response.weather.firstOrNull()?.description ?: "Unknown"
                )

                weatherResults.add(report)
                cityName = ""
            } catch (e: HttpException) {
                val errorReport = WeatherReport(
                    cityName = cityName,
                    temperature = Double.NaN, // NaN = Not a Number
                    condition = "Error: ${e.message()}"
                )
                weatherResults.add(errorReport)
            } catch (e: IOException) {
                val errorReport = WeatherReport(
                    cityName = cityName,
                    temperature = Double.NaN, // NaN = Not a Number
                    condition = "Error: ${e.message}"
                )
                weatherResults.add(errorReport)
            } catch (e: Exception) {
                val errorReport = WeatherReport(
                    cityName = cityName,
                    temperature = Double.NaN, // NaN = Not a Number
                    condition = "Error: ${e.message}"
                )
                weatherResults.add(errorReport)
            } finally {
                isLoading = false // Turn off loading spinner
            }
        }
    }
}

data class WeatherReport(
    val cityName: String,
    val temperature: Double,
    val condition: String
)