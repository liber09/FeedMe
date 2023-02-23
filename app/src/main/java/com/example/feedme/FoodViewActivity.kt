package com.example.feedme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject

class FoodViewActivity : AppCompatActivity(), FoodViewRecyclerAdapter.OnClickListener {

     lateinit var foodRecyclerView : RecyclerView
    lateinit var restaurantid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_view)
        restaurantid = intent.getStringExtra("restId").toString()

        foodRecyclerView = findViewById<RecyclerView>(R.id.RV_Food)
        foodRecyclerView.layoutManager= LinearLayoutManager(this)
        val adapter = FoodViewRecyclerAdapter(this,DataManagerDishes.dishes,this)
        foodRecyclerView.adapter = adapter

        val drinks = findViewById<TextView>(R.id.tv_drinksFoodView)

        val fab_add_dish = findViewById<FloatingActionButton>(R.id.FAB_ADD_Dish)

        fab_add_dish.setOnClickListener{
            val intent = Intent(this, AddNChangeFoodActivity::class.java)

            intent.putExtra("resid", restaurantid)


            startActivity(intent)


        }




        drinks.setOnClickListener {


            val intent = Intent(this,DrinksViewActivity::class.java)

            this.startActivity(intent)
        }

        val backButton = findViewById<ImageView>(R.id.foodViewBackButton)
        backButton.setOnClickListener{
            finish()
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