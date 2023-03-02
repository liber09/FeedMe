package com.example.feedme

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.data.OrderItem
import com.google.firebase.firestore.ktx.toObject

lateinit var recyclerViewRestaurantOrders : RecyclerView
class OrderViewForRestaurants : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_view_for_restaurants)
        val intent: Intent = getIntent()
        val restaurantId = intent.getStringExtra("RESID").toString()
        val tvRestaurantName = findViewById<TextView>(R.id.TVrestaurantOrdersRestaurantTitle)
        tvRestaurantName.text =  intent.getStringExtra("RESNAME").toString()
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