package com.example.feedme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

lateinit var recyclerViewOrderDetails : RecyclerView

class RestaurantOrderDetailsView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_order_details_view)

        recyclerViewOrderDetails = findViewById<RecyclerView>(R.id.RVOrderDetails)
        recyclerViewOrderDetails.layoutManager= LinearLayoutManager(this)
        val adapter = RestaurantOrderDetailsRVAdapter(this,DataManagerOrders.OrderRows)
        recyclerViewOrderDetails.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        recyclerViewOrderDetails.adapter?.notifyDataSetChanged()
    }
}