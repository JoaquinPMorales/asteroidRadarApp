package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import kotlinx.coroutines.launch

class MainViewModel(val database: AsteroidDatabaseDao,
                    application: Application) : AndroidViewModel(application) {

    val asteroids = database.getAllAsteroids()

}