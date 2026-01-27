package com.example.weatherapp.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class WeatherViewModel : ViewModel() {

    var cityName by mutableStateOf("")
    var temperature by mutableStateOf("Unknown")
    var isLoading by mutableStateOf(false)

    fun fetchWeather(apiKey: String) {
        // Using 'viewModelScope' instead of 'rememberCoroutineScope'
        // To keep the behind-the-scenes work alive even if the screen rotates
        viewModelScope.launch {
            isLoading = true // Turn on loading spinner
            try {
                val response = RetrofitInstance.api.getWeather(cityName, apiKey)
                temperature = "${response.main.temperature}°F"
            } catch (e: HttpException) {
                temperature = "Error: ${e.message()}"
            } catch (e: IOException) {
                temperature = "No Internet Connection"
            } catch (e: Exception) {
                temperature = "Unknown Error"
            } finally {
                isLoading = false // Turn off loading spinner
            }
        }
    }
}