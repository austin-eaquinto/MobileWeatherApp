package data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.local.WeatherReport
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    // get all weather reports from the database
    // and return them as a Flow (live data)
    @Query("SELECT * FROM weather_table")
    fun getWeather(): Flow<List<WeatherReport>>

    // insert a new report into the database instead of
    // replacing an existing report with the same city name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(report: WeatherReport)
}