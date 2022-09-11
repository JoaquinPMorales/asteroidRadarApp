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

class AsteroidAdapter : ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(AsteroidDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidAdapter.ViewHolder, position: Int) {
        //Retrieve the item from the data list
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val res = itemView.context.resources

        val date: TextView = itemView.findViewById(R.id.asteroid_date_string)
        val asteroidName: TextView = itemView.findViewById(R.id.asteroid_name_string)
        val hazardousImage: ImageView = itemView.findViewById(R.id.hazardous_image)

        fun bind(item: Asteroid)
        {
            date.text = item.closeApproachDate
            asteroidName.text = item.asteroidId.toString()

            hazardousImage.setImageResource(when(item.isPotentiallyHazardous){
                false -> R.drawable.ic_status_normal
                true -> R.drawable.ic_status_potentially_hazardous
            })
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.asteroid_item, parent, false)
                return ViewHolder(view)
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