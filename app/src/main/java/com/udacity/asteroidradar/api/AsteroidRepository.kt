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
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

enum class DateFilterOptions { SHOW_ALL, SHOW_TODAY, SHOW_WEEK }

class AsteroidRepository(private val database: AsteroidDatabase) {

    private val dateFilterSelected = MutableLiveData<DateFilterOptions>(DateFilterOptions.SHOW_TODAY)

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(Transformations.switchMap(dateFilterSelected) { option ->
            when (option) {
                DateFilterOptions.SHOW_ALL -> database.asteroidDatabaseDao.getAllAsteroids()
                DateFilterOptions.SHOW_WEEK -> database.asteroidDatabaseDao.getWeekAsteroids(currentDate(), fixedDate(Constants.DEFAULT_END_DATE_DAYS))
                else -> database.asteroidDatabaseDao.getDayAsteroids(currentDate())
            }
        }) {
            it.asDomainModel()
        }

    private var _pictureOfTheDay = MutableLiveData<String>()
    val pictureOfTheDay: LiveData<String>
        get() = _pictureOfTheDay

    private var _pictureOfTheDayTittle = MutableLiveData<String>()
    val pictureOfTheDayTittle: LiveData<String>
        get() = _pictureOfTheDayTittle

    fun currentDate(): String {
        val simpleDate = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT)
        return simpleDate.format(Date())
    }

    fun fixedDate(period: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, period)
        return SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).format(
            calendar.time
        )
    }

    private suspend fun refreshAsteroidsList() {
        withContext(Dispatchers.IO) {
            val date = currentDate()
            var result = AsteroidApi.retrofitService.getAsteroids(
                                    date, "", Constants.API_KEY)
            var list = parseAsteroidsJsonResult(JSONObject(result))
            Log.i("AsteroidRepository", "list size: ${list.size}")
            database.asteroidDatabaseDao.insertAll(*list.asDomainModel())
        }
    }

    private suspend fun refreshPictureOfTheDay() {
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
            refreshAsteroidsList()
        } catch (e: Exception) {
            Log.e("AsteroidRepository", "Exception ${e.message}")
        }
    }

    fun updateFilter(dateFilter: DateFilterOptions) {
        dateFilterSelected.value = dateFilter
        }
}