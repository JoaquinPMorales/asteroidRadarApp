package com.udacity.asteroidradar

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidItemBinding

class AsteroidAdapter(val clickListener: AsteroidListener) : ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(AsteroidDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidAdapter.ViewHolder, position: Int) {
        //Retrieve the item from the data list
        val item = getItem(position)
        holder.bind(clickListener, getItem(position)!!)
    }

    class ViewHolder private constructor (val binding: AsteroidItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: AsteroidListener, item: Asteroid)
        {
            val res = itemView.context.resources
            binding.asteroidDateString.text = item.closeApproachDate
            binding.asteroidNameString.text = item.asteroidId.toString()
            binding.clickListener = clickListener
            binding.executePendingBindings()
            /*binding.hazardousImage.setImageResource(when(item.isPotentiallyHazardous){
                false -> R.drawable.ic_status_normal
                true -> R.drawable.ic_status_potentially_hazardous
            })*/
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.asteroidId == newItem.asteroidId
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }
    }
}

class AsteroidListener(val clickListener: (asteroidId: Long) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid.asteroidId)
}