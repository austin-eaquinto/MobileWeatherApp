package data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import data.WeatherDao

// this is to tell Room that this is a database
@Database(entities = [WeatherReport::class], version = 1)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}