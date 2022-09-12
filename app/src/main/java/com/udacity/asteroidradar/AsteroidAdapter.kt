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

class AsteroidAdapter(val clickListener: AsteroidListener) : ListAdapter<com.udacity.asteroidradar.Asteroid, AsteroidAdapter.ViewHolder>(AsteroidDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidAdapter.ViewHolder, position: Int) {
        //Retrieve the item from the data list
        val item = getItem(position)
        holder.bind(clickListener, getItem(position)!!)
    }

    class ViewHolder private constructor (val binding: AsteroidItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: AsteroidListener, item: com.udacity.asteroidradar.Asteroid)
        {
            val res = itemView.context.resources
            binding.asteroid = item
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

    class AsteroidDiffCallback : DiffUtil.ItemCallback<com.udacity.asteroidradar.Asteroid>() {
        override fun areItemsTheSame(oldItem: com.udacity.asteroidradar.Asteroid, newItem: com.udacity.asteroidradar.Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: com.udacity.asteroidradar.Asteroid, newItem: com.udacity.asteroidradar.Asteroid): Boolean {
            return oldItem == newItem
        }
    }
}

class AsteroidListener(val clickListener: (asteroid: com.udacity.asteroidradar.Asteroid) -> Unit) {
    fun onClick(asteroid: com.udacity.asteroidradar.Asteroid) = clickListener(asteroid)
}