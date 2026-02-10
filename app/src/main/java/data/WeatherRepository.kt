package data

import data.remote.RetrofitInstance
import data.local.WeatherReport
import retrofit2.HttpException
import java.io.IOException

class WeatherRepository {

    suspend fun getWeather(city: String, apiKey: String): WeatherReport {
        return try {
            val response = RetrofitInstance.api.getWeather(city, apiKey)

            WeatherReport(
                cityName = city,
                temperature = response.main.temperature,
                condition = response.weather.firstOrNull()?.description ?: "Unknown"
            )
        } catch (e: Exception) {
            val errorReport = when (e) {
                is HttpException -> "Error: ${e.message()}"
                is IOException -> "Network Error"
                else -> "Unkown Error: ${e.message}"
            }

            WeatherReport(
                cityName = city,
                temperature = Double.NaN,
                condition = errorReport
            )
        }
    }
}