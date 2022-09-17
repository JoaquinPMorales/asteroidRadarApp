package com.udacity.asteroidradar.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.asDomainModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDatabaseDao.getAllAsteroids()) {
            it.asDomainModel()
        }

    private var _pictureOfTheDay = MutableLiveData<String>()
    val pictureOfTheDay: LiveData<String>
        get() = _pictureOfTheDay

    private var _pictureOfTheDayTittle = MutableLiveData<String>()
    val pictureOfTheDayTittle: LiveData<String>
        get() = _pictureOfTheDayTittle

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            var result = AsteroidApi.retrofitService.getAsteroids(
                                    "2022-09-16", "2022-09-16", Constants.API_KEY)
            Log.i("AsteroidRepository", "json: $result")
            var list = parseAsteroidsJsonResult(JSONObject(result))
            Log.i("AsteroidRepository", "parseAsteroids finished, now to DB")
            Log.i("AsteroidRepository", "list size: ${list.size}")
            database.asteroidDatabaseDao.insertAll(*list.asDomainModel())
        }
    }

    suspend fun refreshPictureOfTheDay() {
        withContext(Dispatchers.IO) {
            Log.i("AndroidRepository", "refreshPictureOfTheDay")
            val pictureOfTheDay = AsteroidApi.retrofitService.getPictureOfTheDay(API_KEY)
            _pictureOfTheDayTittle.postValue(pictureOfTheDay.title)
            if(pictureOfTheDay.mediaType.equals("image"))
            {
                _pictureOfTheDay.postValue(pictureOfTheDay.url)
            }
        }
    }

    suspend fun refreshAll() {
        try {
            refreshPictureOfTheDay()
            refreshAsteroids()
        } catch (e: HttpException) {
            Log.e("AsteroidRepository", "HttpException ${e.message()}")
        } catch (e: SocketException) {
            Log.e("AsteroidRepository", "SocketException ${e.message}")
        } catch (e: SocketTimeoutException) {
            Log.e("AsteroidRepository", "SocketTimeoutException ${e.message}")
        } catch (e: UnknownHostException) {
            Log.e("AsteroidRepository", "UnknownHostException ${e.message}")
        }
    }
}