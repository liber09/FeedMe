package com.example.feedme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject

class FoodViewActivity : AppCompatActivity() {

     lateinit var foodRecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_view)


        foodRecyclerView = findViewById<RecyclerView>(R.id.RV_Food)
        foodRecyclerView.layoutManager= LinearLayoutManager(this)
        val adapter = FoodViewRecyclerAdapter(this,DataManagerDishes.dishes)
        foodRecyclerView.adapter = adapter

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val drinks = findViewById<TextView>(R.id.tv_drinksFoodView)

        drinks.setOnClickListener {


            val intent = Intent(this,DrinksViewActivity::class.java)

            this.startActivity(intent)
        }


        val dessertText = findViewById<TextView>(R.id.tv_Deserts)

        dessertText.setOnClickListener {

            val desserts = dishes.category.equals("Dessert")

        }

    }


    override fun onResume() {
        super.onResume()


        foodRecyclerView.adapter?.notifyDataSetChanged()

    }


}