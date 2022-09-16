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

enum class AsteroidApiStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()

    val pictureOfTheDay: LiveData<PictureOfDay>
        get() = _pictureOfTheDay

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail
        get() = _navigateToAsteroidDetail

    private val database = getInstance(application)
    private val asteroidRepository = AsteroidRepository(database)

    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
    }

    val asteroidList = asteroidRepository.asteroids

    fun onAsteroidDetailClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail.value = null
    }

//    private fun getPictureOfTheDay(){
//        viewModelScope.launch {
//            try {
//                var pictureOfTheDay = AsteroidApi.retrofitService.getPictureOfTheDay(API_KEY)
//                if(pictureOfTheDay.mediaType.equals("image"))
//                {
//                    _pictureOfTheDay.value = pictureOfTheDay
//                }
//            }
//            catch (e: Exception)
//            {
//                Log.i("MainViewModel", "Error trying to get pictureOfTheDay")
//                Log.i("MainViewModel","exception: ${e.toString()}")
//            }
//        }
//    }

}