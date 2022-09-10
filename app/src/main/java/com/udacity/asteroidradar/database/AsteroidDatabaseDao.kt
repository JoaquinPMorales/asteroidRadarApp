package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface AsteroidDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: Asteroid)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param asteroid new value to write
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(asteroid: Asteroid)

    /**
     * Selects and returns the row that matches the asteroidId, which is our key.
     *
     * @param key asteroidId to match
     */
    @Query("SELECT * from asteroid_radar_table WHERE asteroidId = :key")
    suspend fun get(key: Long): Asteroid?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM asteroid_radar_table")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM asteroid_radar_table ORDER BY close_approach_date DESC")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    /**
     * Selects and returns the asteroid with given asteroidId.
     */
    @Query("SELECT * from asteroid_radar_table WHERE asteroidId = :key")
    fun getAsteroidWithId(key: Long): LiveData<Asteroid>
}

