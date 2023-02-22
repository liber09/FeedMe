package com.example.feedme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RestaurantViewActiviity : AppCompatActivity(), RestaurantViewRVAdapter.OnClickListener {

    lateinit var restaurantRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_view_activiity)

        restaurantRecyclerView = findViewById(R.id.rv_Restaurant)
        restaurantRecyclerView.layoutManager = LinearLayoutManager(this)
        restaurantRecyclerView.adapter = RestaurantViewRVAdapter(this,DataManagerRestaurants.restaurants, this)







    }
    override fun onResume() {
        super.onResume()



        restaurantRecyclerView.adapter?.notifyDataSetChanged()




    }

        override fun OnClick(position: Int) {
        val intent = Intent(this, RestaurantDetailsActivity::class.java)
            val restId =DataManagerRestaurants.restaurants[position].documentId.toString()

        intent.putExtra("id", restId)
            Log.d("TTT","$restId")
        startActivity(intent)


    }
}