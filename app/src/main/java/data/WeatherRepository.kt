package data

import data.remote.RetrofitInstance
import data.local.WeatherReport
import data.remote.RetrofitInstance.api
import data.remote.WeatherResponse
import data.remote.toEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

// Intermediary between WeatherViewModel and Retrofit
// Hides the implementation details of the network request
class WeatherRepository(private val dao: WeatherDao) {

    suspend fun getWeather(city: String, apiKey: String): WeatherReport {
        return try {
            // create a report from the response
            val response = RetrofitInstance.api.getWeather(city, apiKey)
            // parse the response to an entity
            val report = response.toEntity()
            // save the report to the database
            saveWeatherToDatabase(report)
            // return the report
            report
        } catch (e: Exception) {
            val errorReport = when (e) {
                is HttpException -> "Error: ${e.message()}"
                is IOException -> "Network Error"
                else -> "Unknown Error: ${e.message}"
            }

            WeatherReport(
                cityName = city,
                temperature = Double.NaN,
                condition = errorReport
            )
        }
    }

    // get weather from the database at the same time as the view model
    fun getWeatherFromDatabase(): Flow<List<WeatherReport>> = dao.getWeather()

    // save weather to the database at the same time as the view model
    suspend fun saveWeatherToDatabase(report: WeatherReport) = dao.insertWeather(report)
}