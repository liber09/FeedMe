package com.example.feedme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.R.id.*

class DrinksViewActivity : AppCompatActivity() {

    lateinit var drinksRecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinks_view)

        val home = findViewById<ImageButton>(R.id.ibtn_home_drinksView)
        home.setOnClickListener{
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent) }

        val cart = findViewById<ImageButton>(R.id.cartButton)
        cart.setOnClickListener{
            val intent = Intent(this,ShoppingCart::class.java)
            startActivity(intent)

            val huvudrätt = findViewById<TextView>(R.id.huvudrättTextView)
            huvudrätt.setOnClickListener{
                val intent = Intent(this,FoodViewActivity::class.java)
                startActivity(intent)

                val dessert = findViewById<TextView>(R.id.dessertTextView)
                dessert.setOnClickListener{
                    val desserts = dishes.category.equals("Dessert")
                }
            }



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
