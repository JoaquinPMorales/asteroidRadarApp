package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.AsteroidRepository
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getInstance
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail
        get() = _navigateToAsteroidDetail

    private val database = getInstance(application)
    val asteroidRepository = AsteroidRepository(database)
    val asteroidList = asteroidRepository.asteroids

    init {
        viewModelScope.launch {
            asteroidRepository.refreshAll()
        }
    }

    fun onAsteroidDetailClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail.value = null
    }

}