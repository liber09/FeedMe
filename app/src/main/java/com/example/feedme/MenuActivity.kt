package com.example.feedme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val forratt = findViewById<Button>(R.id.bt_1)
        val varmratt = findViewById<Button>(R.id.bt_2)
        val dessert = findViewById<Button>(R.id.bt_3)
        val drinks = findViewById<Button>(R.id.bt_4)

        forratt.setOnClickListener{
            val intent= Intent(this,FoodViewActivity::class.java)
            startActivity(intent) }
        varmratt.setOnClickListener{
            val intent= Intent(this,RestaurantViewActiviity::class.java)
            startActivity(intent) }
        dessert.setOnClickListener{
            val intent= Intent(this,RestaurantViewActiviity::class.java)
            startActivity(intent) }
        drinks.setOnClickListener{
            val intent= Intent(this,DrinksViewActivity::class.java)
            startActivity(intent) }


    }
}