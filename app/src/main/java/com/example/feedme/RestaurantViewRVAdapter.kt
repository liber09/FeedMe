package com.example.feedme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.data.Restaurant

class RestaurantViewRVAdapter(
    val context: Context,
    val restaurants: List<Restaurant>,
    val listener : OnClickListener
) :

    RecyclerView.Adapter<RestaurantViewRVAdapter.ViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater
            .inflate(R.layout.list_item_restaurant, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val restaurant = restaurants[position]

        if (restaurant.imagePath.isEmpty()) {
            holder.restaurantImage.setImageResource(R.drawable.logo)

        }
        holder.restaurantTitle.text = restaurant.name

        if (restaurant.description.isNotEmpty()) {
            holder.restaurantDescription.text = restaurant.description
        } else {
            holder.restaurantDescription.text = "Ingen Beskrvining tillgänglig"
        }

        //change toloop
        if ((restaurant.rating != null) && (restaurant.rating == 5.0)) {

            holder.fivestars.setImageResource(R.drawable.ic_baseline_stars_24)
            holder.fourstars.setImageResource(R.drawable.ic_baseline_stars_24)
            holder.threestars.setImageResource(R.drawable.ic_baseline_stars_24)
            holder.twostars.setImageResource(R.drawable.ic_baseline_stars_24)
            holder.onestar.setImageResource(R.drawable.ic_baseline_stars_24)


        }

        if ((restaurant.rating != null) && (restaurant.rating >= 4) && (restaurant.rating <= 5)) {

            holder.fourstars.setImageResource(R.drawable.ic_baseline_stars_24)
            holder.threestars.setImageResource(R.drawable.ic_baseline_stars_24)
            holder.twostars.setImageResource(R.drawable.ic_baseline_stars_24)
            holder.onestar.setImageResource(R.drawable.ic_baseline_stars_24)


        }
        if ((restaurant.rating != null) && (restaurant.rating >= 3) && (restaurant.rating <= 4)) {
            holder.threestars.setImageResource(R.drawable.ic_baseline_stars_24)
            holder.twostars.setImageResource(R.drawable.ic_baseline_stars_24)
            holder.onestar.setImageResource(R.drawable.ic_baseline_stars_24)

        }
        if ((restaurant.rating != null) && (restaurant.rating >= 2) && (restaurant.rating <= 3)) {

            holder.twostars.setImageResource(R.drawable.ic_baseline_stars_24)
            holder.onestar.setImageResource(R.drawable.ic_baseline_stars_24)
        }

        if ((restaurant.rating != null) && (restaurant.rating >= 1) && (restaurant.rating <= 2)) {


            holder.onestar.setImageResource(R.drawable.ic_baseline_stars_24)
        }


    }

    override fun getItemCount() = restaurants.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantTitle = itemView.findViewById<TextView>(R.id.tv_rvRest_title)
        val restaurantDescription = itemView.findViewById<TextView>(R.id.tv_rvRest_Description)
        val restaurantImage = itemView.findViewById<ImageView>(R.id.iv_RV_restaurant)
        val onestar = itemView.findViewById<ImageView>(R.id.IVOrderWarning)
        val twostars = itemView.findViewById<ImageView>(R.id.IVOrderDatailWOrWarning)
        val threestars = itemView.findViewById<ImageView>(R.id.iv_star3)
        val fourstars = itemView.findViewById<ImageView>(R.id.iv_star4)
        val fivestars = itemView.findViewById<ImageView>(R.id.iv_star5)

        init {
            itemView.setOnClickListener {
                val postion = adapterPosition
                listener.OnClick(postion)

            }


        }
    }
        interface OnClickListener {
            fun OnClick(position: Int)


        }
}