package com.example.feedme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.State.restaurantId

class FoodViewActivity : AppCompatActivity(), FoodViewRecyclerAdapter.OnClickListener {

    lateinit var foodRecyclerView: RecyclerView
    lateinit var restaurantid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_view)

        restaurantid = intent.getStringExtra("restid").toString()
        Log.d("KKK", restaurantid)




        foodRecyclerView = findViewById<RecyclerView>(R.id.RV_Food)
        foodRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = FoodViewRecyclerAdapter(this, DataManagerDishes.dishes, this)
        foodRecyclerView.adapter = adapter

        val drinks = findViewById<TextView>(R.id.tv_drinksFoodView)

        drinks.setOnClickListener {


            val intent = Intent(this, DrinksViewActivity::class.java)

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

    override fun OnClick(position: Int) {
        val intent = Intent(this, AddNChangeFoodActivity::class.java)


        intent.putExtra("resid", restaurantid)
        intent.putExtra(DISH_POSTION_KEY, position)

        Log.d("LLL",restaurantid)

        startActivity(intent)

    }


}