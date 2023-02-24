package com.example.feedme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

lateinit var recyclerViewRestaurantOrders : RecyclerView
class OrderViewForRestaurants : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_view_for_restaurants)

        recyclerViewRestaurantOrders = findViewById<RecyclerView>(R.id.RvRestaurantOrders)
        recyclerViewRestaurantOrders.layoutManager = LinearLayoutManager(this)
        val adapter = RestaurantOrdersRVAdapter(this, DataManagerOrders.orders)
        recyclerViewRestaurantOrders.adapter = adapter
    }
    override fun onResume() {
        super.onResume()
        recyclerViewRestaurantOrders.adapter?.notifyDataSetChanged()
    }

}