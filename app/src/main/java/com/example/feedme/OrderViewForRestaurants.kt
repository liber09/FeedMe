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
        getOrdersForRestaurant(restaurantId)
        recyclerViewRestaurantOrders = findViewById<RecyclerView>(R.id.RvRestaurantOrders)
        recyclerViewRestaurantOrders.layoutManager = LinearLayoutManager(this)
        val adapter = RestaurantOrdersRVAdapter(this, DataManagerOrders.orders)
        recyclerViewRestaurantOrders.adapter = adapter


    }
    override fun onResume() {
        super.onResume()
        recyclerViewRestaurantOrders.adapter?.notifyDataSetChanged()
    }
    fun getOrdersForRestaurant(restaurantId: String){
        DataManagerOrders.orders.clear()
        var index = 0
        db.collection("restaurants").document(restaurantId).collection("orders")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    DataManagerOrders.orders.add(document.toObject())
                    db.collection("restaurants").document(restaurantId).collection("orders").document(document.id).collection("orderedDishes")
                        .get()
                        .addOnSuccessListener { orderItems ->
                            for(orderItem in orderItems) {
                                DataManagerOrders.orders.get(index).orderedDishes.add(orderItem.toObject<OrderItem>())
                                Log.d(ContentValues.TAG, "${orderItem.id} => ${orderItem.data}")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                        }
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

}