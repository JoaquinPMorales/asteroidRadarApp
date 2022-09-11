package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.database.Asteroid

class AsteroidAdapter : RecyclerView.Adapter<AsteroidAdapter.ViewHolder>() {

    var data = listOf<Asteroid>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.asteroid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Retrieve the item from the data list
        val item = data[position]
        val res = holder.itemView.context.resources
        holder.date.text = data.get(position).closeApproachDate
        holder.asteroidName.text = data.get(position).asteroidId.toString()

        holder.hazardousImage.setImageResource(when(item.isPotentiallyHazardous){
            false -> R.drawable.ic_status_normal
            true -> R.drawable.ic_status_potentially_hazardous
        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val date: TextView = itemView.findViewById(R.id.asteroid_date_string)
        val asteroidName: TextView = itemView.findViewById(R.id.asteroid_name_string)
        val hazardousImage: ImageView = itemView.findViewById(R.id.hazardous_image)
    }

}