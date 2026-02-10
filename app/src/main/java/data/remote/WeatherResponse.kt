package data.remote
// Tells the translator to look for a JSON field named "temp" and put its value
// into the variable "temperature"
import com.google.gson.annotations.SerializedName


data class WeatherResponse (
    @SerializedName("main")
    val main: MainWeather,

    @SerializedName("weather")
    val weather: List<WeatherDescription>,

    @SerializedName("name")
    val cityName: String
)

data class MainWeather (
    @SerializedName("temp")
    val temperature: Double,

    @SerializedName("humidity")
    val humidity: Int
)

data class WeatherDescription (
    @SerializedName("main")
    val main: String,

    @SerializedName("description")
    val description: String
)