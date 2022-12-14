package com.udacity.asteroidradar

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("pictureOfTheDay")
fun bindImageOfTheDay(imageView: ImageView, imgUrl: String?)
{
    Picasso.get()
        .load(imgUrl)
        .networkPolicy(NetworkPolicy.OFFLINE)
        .into(imageView, object : Callback {
            override fun onSuccess() {}
            override fun onError(e: Exception?) {
                Log.e("bindImageOfTheDay", "onError: ${e?.message}")
                // Try again online if cache failed
                Picasso.get()
                    .load(imgUrl)
                    .placeholder(R.drawable.placeholder_picture_of_day)
                    .error(R.drawable.ic_broken_image)
                    .into(imageView)
            }
        })
}

@BindingAdapter("pictureOfTheDayTitle")
fun bindImageOfTheDayTitle(imageView: ImageView, title: String?){
    if (title != null)
    {
        imageView.contentDescription = String.format(imageView.context.getString(R.string.nasa_picture_of_day_content_description_format), title)
    }
}

@BindingAdapter("listOfAsteroids")
fun bindListOfAsteroids(recyclerView: RecyclerView, asteroidList: List<Asteroid>?)
{
    Log.i("BindingAdapters", "inside bindListOfAsteroids")
    val adapter = recyclerView.adapter as AsteroidAdapter
    Log.i("BindingAdapters", "asteroidList size: ${asteroidList?.size}")
    adapter.submitList(asteroidList)
}
