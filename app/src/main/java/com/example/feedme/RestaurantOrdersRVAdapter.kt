package com.example.feedme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.data.Order
import com.example.feedme.data.Restaurant

class RestaurantOrdersRVAdapter(
    val context: Context,
    val orders: List<Order>
    ) :

    RecyclerView.Adapter<RestaurantOrdersRVAdapter.ViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantOrdersRVAdapter.ViewHolder {
        val itemView = layoutInflater
            .inflate(R.layout.list_item_restaurant_order_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RestaurantOrdersRVAdapter.ViewHolder, position: Int) {

        val order = orders[position]

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOrderTime = itemView.findViewById<TextView>(R.id.TVResOrderTime)
        val btnReadyForPickup = itemView.findViewById<TextView>(R.id.btnReadyForPickup)
        val btnSendMessage = itemView.findViewById<Button>(R.id.btnSendMessage)
        val btnDelayed = itemView.findViewById<Button>(R.id.btnDelayed)
        val btnSeeMore = itemView.findViewById<Button>(R.id.btnSeeMore)
        val ivOrderWarning = itemView.findViewById<Button>(R.id.IVOrderWarning)
        val TVOrderCustomerNumber = itemView.findViewById<TextView>(R.id.TVOrderCustomerNumber)
        val TVOrderCustomerPhone = itemView.findViewById<TextView>(R.id.TVOrderCustomerPhone)

    }}