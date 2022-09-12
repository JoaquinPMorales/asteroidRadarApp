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
import com.udacity.asteroidradar.api.PictureOfTheDayApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(val database: AsteroidDatabaseDao,
                    application: Application) : AndroidViewModel(application) {

    val asteroids = database.getAllAsteroids()

    private val _listOfAsteroids = MutableLiveData<ArrayList<Asteroid>>()

    val listOfAsteroids : LiveData<ArrayList<Asteroid>>
        get() = _listOfAsteroids

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()

    val pictureOfTheDay: LiveData<PictureOfDay>
        get() = _pictureOfTheDay

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail
        get() = _navigateToAsteroidDetail

    fun onAsteroidDetailClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail.value = null
    }

    init {
        getPictureOfTheDay()
        getAsteroidsProperties()
    }

    private fun getAsteroidsProperties(){
        viewModelScope.launch {
            try {
                var result = AsteroidApi.retrofitService.getAsteroids("2022-09-07", "2022-09-08", API_KEY)
                Log.i("MainViewModel", "json: $result")

//                var jsonObject : JSONObject(result)
//                _listOfAsteroids.value = parseAsteroidsJsonResult(result)
//                Log.i("MainViewModel", "listOfAsteroids: ${_listOfAsteroids.toString()}")
            }
            catch (e: Exception)
            {
                Log.i("MainViewModel", "Error trying to get listOfAsteroids")
                Log.i("MainViewModel","exception: ${e.toString()}")
            }
        }
    }

    private fun getPictureOfTheDay(){
        viewModelScope.launch {
            try {
                var pictureOfTheDay = PictureOfTheDayApi.retrofitService.getPictureOfTheDay(API_KEY)
                if(pictureOfTheDay.mediaType.equals("image"))
                {
                    _pictureOfTheDay.value = pictureOfTheDay
                }
            }
            catch (e: Exception)
            {

            }
        }
    }

}