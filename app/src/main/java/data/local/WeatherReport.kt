package data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity tells Room: "Make a table for this class"
@Entity(tableName = "weather_table")
data class WeatherReport(
    // @PrimaryKey tells Room: "The city name is the unique ID"
    @PrimaryKey
    val cityName: String,
    val temperature: Double,
    val condition: String
)