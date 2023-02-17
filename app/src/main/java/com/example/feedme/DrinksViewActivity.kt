package com.example.feedme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.R.id.*

class DrinksViewActivity : AppCompatActivity() {

    lateinit var drinksRecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinks_view)

        val home = findViewById<Button>(R.id.menuButton1)
        home.setOnClickListener{
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent) }


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
