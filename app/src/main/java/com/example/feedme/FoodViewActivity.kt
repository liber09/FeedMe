package com.example.feedme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FoodViewActivity : AppCompatActivity() {

     lateinit var foodRecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_view)


        foodRecyclerView = findViewById<RecyclerView>(R.id.RV_Food)
        foodRecyclerView.layoutManager= LinearLayoutManager(this)

        val adapter = FoodViewRecyclerAdapter(this,DataManagerDishes.dishes)

        foodRecyclerView.adapter = adapter


    }

    override fun onResume() {
        super.onResume()

        foodRecyclerView.adapter?.notifyDataSetChanged()


    }

}