package com.example.weatherapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import data.AppDatabase
import data.WeatherRepository
import data.local.WeatherReport
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class WeatherViewModel(private val repo: WeatherRepository) : ViewModel() {

    var cityName by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    val weatherList = repo.getWeatherFromDatabase().stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = emptyList())


    fun fetchWeather(apiKey: String) {
        viewModelScope.launch {
            isLoading = true // Turn on the 'loading spinner'
            val report = repo.getWeather(cityName, apiKey)
            isLoading = false // Turn off loading spinner
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // 1. Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                // 2. Build the database and repository
                val database = AppDatabase.getDatabase(application)
                val repository = WeatherRepository(database.weatherDao())

                // 3. Return the ViewModel with the repository injected
                return WeatherViewModel(repository) as T
            }
        }
    }
}