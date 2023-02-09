package com.example.feedme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.feedme.data.Restaurant

//Todo byta dishes till restaurants när all data är ner

class DeliveryPersonViewActivity : AppCompatActivity() {

    lateinit var deliveryRestaurantRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_person_view)

        deliveryRestaurantRecyclerView = findViewById(R.id.RV_DeliveryViewRest)

        deliveryRestaurantRecyclerView.layoutManager = LinearLayoutManager(this)
        deliveryRestaurantRecyclerView.adapter =
            CollectOrderRecyclerAdapter(this,
                DataManagerDishes.dishes
            )





    }
    override fun onResume() {
        super.onResume()

       deliveryRestaurantRecyclerView.adapter?.notifyDataSetChanged()


    }

}