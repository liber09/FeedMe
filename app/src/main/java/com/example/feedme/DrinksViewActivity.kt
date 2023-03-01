package com.example.feedme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.R.id.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DrinksViewActivity : AppCompatActivity() {

    lateinit var drinksRecyclerView : RecyclerView
    lateinit var auth: FirebaseAuth
    lateinit var userUID : String
    lateinit var restaurantid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinks_view)



        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val cart = findViewById<ImageButton>(R.id.cartButton)
        cart.setOnClickListener{
            val intent = Intent(this,ShoppingCart::class.java)
            startActivity(intent)


                }


        //TODO - logic that the shoppingcard funktions while switching backnfort
        // and that it actually refers to the one that is in


        val mainCourseTV = findViewById<TextView>(R.id.tv_maincoursesDrinkView)
        mainCourseTV.setOnClickListener {
            finish()
        }

        val dessert = findViewById<TextView>(R.id.tv_dessertsDrinkView)


        val starter = findViewById<TextView>(R.id.tv_startersDrinkView)
        starter.setOnClickListener { finish() }
        dessert.setOnClickListener {
            finish()

        }







        drinksRecyclerView = findViewById<RecyclerView>(RV_Drinks)
        drinksRecyclerView.layoutManager= LinearLayoutManager(this)
        val adapter = DrinksViewRecyclerAdapter(this,DataManagerDrinks.drinkList)
        drinksRecyclerView.adapter = adapter

        val fab_add_drink = findViewById<FloatingActionButton>(R.id.FAB_ADD_Drink)
        fab_add_drink.isInvisible = true

        auth = Firebase.auth
        val user = auth.currentUser
        userUID = user!!.uid.toString()
        restaurantid = intent.getStringExtra("restId").toString()

        if (user != null) {
            val n =2
            val userId = restaurantid.dropLast(n)

            if (userUID == userId){
                fab_add_drink.isVisible = true

            } }


        fab_add_drink.setOnClickListener{
            val intent = Intent(this, AddNChangeDrinks::class.java)

            intent.putExtra("resid", restaurantid)


            startActivity(intent)


        }





    }

    override fun onResume() {
        super.onResume()

        drinksRecyclerView.adapter?.notifyDataSetChanged()

    }
}
