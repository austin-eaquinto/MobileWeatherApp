package data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import data.local.WeatherReport

@Database(entities = [WeatherReport::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    // allows the app to access the DAO
    abstract fun weatherDao(): WeatherDao

    companion object {
        // @Volatile means: "don't cache this variable, check in main memory"
        // prevents creation of multiple instances of the database
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // If the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                // creates a new database instance on the device's storage
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "weather_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}