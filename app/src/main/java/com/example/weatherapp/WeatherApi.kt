package com.example.weatherapp

import retrofit2.http.GET
import retrofit2.http.Query

// Function that tell Retrofit "Go to the website ending in /data/2.5/weather"
interface WeatherApi {
    @GET("/data/2.5/weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "imperial" // Defaults to Fahrenheit
    ): WeatherResponse
}