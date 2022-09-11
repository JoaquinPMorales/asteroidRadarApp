package com.udacity.asteroidradar

import android.content.res.Resources
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
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Retrieve the item from the data list
        val item = data[position]
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

}