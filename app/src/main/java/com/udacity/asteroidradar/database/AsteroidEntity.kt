package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroid_radar_table")
data class AsteroidEntity(
    @PrimaryKey
    var asteroidId : Long = 0L,

    @ColumnInfo(name = "")
)