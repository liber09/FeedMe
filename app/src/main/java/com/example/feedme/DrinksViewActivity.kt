package com.example.feedme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.R.id.*

class DrinksViewActivity : AppCompatActivity() {

    lateinit var drinksRecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinks_view)


        val cart = findViewById<ImageButton>(R.id.cartButton)
        cart.setOnClickListener{
            val intent = Intent(this,ShoppingCart::class.java)
            startActivity(intent)


        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        drinksRecyclerView = findViewById<RecyclerView>(RV_Drinks)
        drinksRecyclerView.layoutManager= LinearLayoutManager(this)
        val adapter = DrinksViewRecyclerAdapter(this,DataManagerDrinks.drinkList)
        drinksRecyclerView.adapter = adapter

    }

    override fun onResume() {
        super.onResume()

        drinksRecyclerView.adapter?.notifyDataSetChanged()

    }
}
