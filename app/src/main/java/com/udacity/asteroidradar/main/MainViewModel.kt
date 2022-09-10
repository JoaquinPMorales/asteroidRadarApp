package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidDatabaseDao

class MainViewModel(val database: AsteroidDatabaseDao,
                    application: Application) : AndroidViewModel(application) {

}