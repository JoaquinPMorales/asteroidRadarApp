package com.udacity.asteroidradar

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class PictureOfDay(@Json(name = "media_type") val mediaType: String, val title: String,
                        val url: String)

@JsonClass(generateAdapter = true)
data class AsteroidObjectContainer(val asteroids: List<ObjectAsteroid>)

@JsonClass(generateAdapter = true)
data class ObjectAsteroid(
    val id: Long,
    val name: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)