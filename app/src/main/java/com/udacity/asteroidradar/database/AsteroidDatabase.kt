package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * A database that stores Asteroids information.
 * And a global method to get access to the database.
 *
 * This pattern is pretty much the same for any database,
 * so you can reuse it.
 */
@Database(entities = [AsteroidEntity::class], version = 2, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract val asteroidDatabaseDao: AsteroidDatabaseDao

    /**
     * Define a companion object, this allows us to add functions on the SleepDatabase class.
     *
     * For example, clients can call `SleepDatabase.getInstance(context)` to instantiate
     * a new SleepDatabase.
     */
    companion object {

        private lateinit var INSTANCE: AsteroidDatabase

        fun getInstance(context: Context): AsteroidDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "asteroid_radar_table"
                    ).fallbackToDestructiveMigration()
                     .build()
                }
                // Return instance; smart cast to be non-null.
                return INSTANCE
            }
        }
    }
}
