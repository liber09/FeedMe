package com.example.feedme

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.data.Restaurant

class RestaurantViewRVAdapter(val context: Context,
                              val restaurants: List<Restaurant>) :

    RecyclerView.Adapter<RestaurantViewRVAdapter.ViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater
            .inflate(R.layout.list_item_restaurant, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val restaurant = restaurants[position]

        if (restaurant.imagePath.isEmpty()){
            holder.restaurantImage.setImageResource(R.drawable.logo)

        }
        holder.restaurantTitle.text = restaurant.name

        if (restaurant.description.isNotEmpty()){
            holder.restaurantDescription.text = restaurant.description
        } else {holder.restaurantDescription.text = "Ingen Beskrvining tillgänglig"}


    }

    override fun getItemCount()= restaurants.size



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantTitle = itemView.findViewById<TextView>(R.id.tv_rvRest_title)
        val restaurantDescription = itemView.findViewById<TextView>(R.id.tv_rvRest_Description)
        val restaurantImage = itemView.findViewById<ImageView>(R.id.iv_RV_restaurant)


    }


}