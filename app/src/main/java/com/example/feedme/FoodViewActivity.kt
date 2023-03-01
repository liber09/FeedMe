package com.example.feedme

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.data.Customer
import com.example.feedme.data.Dishes
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FoodViewActivity : AppCompatActivity(), FoodViewRecyclerAdapter.OnClickListener {

     lateinit var foodRecyclerView : RecyclerView
    lateinit var restaurantid: String
    lateinit var auth: FirebaseAuth
    lateinit var userUID : String
    lateinit var category: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_view)


        auth = Firebase.auth
        val user = auth.currentUser
        category = "Förrätt"


        //TODO plocka bort när allt sitta
       if(user != null) {
        userUID = user.uid.toString()}
        restaurantid = intent.getStringExtra("restId").toString()

        foodRecyclerView = findViewById<RecyclerView>(R.id.RV_Food)
        foodRecyclerView.layoutManager= LinearLayoutManager(this)
        val adapter = FoodViewRecyclerAdapter(this,DataManagerDishes.dishes,this,)
        foodRecyclerView.adapter = adapter
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val dishReference = db.collection("restaurants")
            .document("${restaurantid}")
            .collection("dishes")
        dishReference.addSnapshotListener{ snapshot, exception ->
            if (exception != null || snapshot ==null)
            {
                Log.d("lalaa", "Exception")
                return@addSnapshotListener
            }
            val dishlist = snapshot.toObjects(Dishes::class.java)
            for (dish in dishlist){
                Log.d("Dish","$dish")
               var förrätt = dish.category == "Förrätt"
                Log.d("Starter", "$förrätt")
            }
          /*  for (document in snapshot.documents){
                Log.d("yyy", "Document ${document.id}: ${document.data}")

            }*/

        }

        val drinks = findViewById<TextView>(R.id.tv_drinksFoodView)
        val starters = findViewById<TextView>(R.id.tv_starterMealView)
        val mainCourses = findViewById<TextView>(R.id.tv_MainCourse)
        val desserts= findViewById<TextView>(R.id.tv_Deserts)

        val fab_add_dish = findViewById<FloatingActionButton>(R.id.FAB_ADD_Drink)
        fab_add_dish.isInvisible = true



// when creating a restaurant we start with adding +1 at the end at the moment, and are planing to extend that
// så that the same restaurantowner can be linked to all his restaurants - if we code in more restaurant
        // of course this is not the best solution yet

        if (user != null) {
            val n =2
            val userId = restaurantid.dropLast(n)

            if (userUID == userId){
                fab_add_dish.isVisible = true

            } }


        starters.setOnClickListener {
            starters.setTypeface(null,Typeface.BOLD)
            drinks.setTypeface(null,Typeface.NORMAL)
            mainCourses.setTypeface(null,Typeface.NORMAL)
            desserts.setTypeface(null,Typeface.NORMAL)
            category = "Förrätt"





        }

        mainCourses.setOnClickListener {
            starters.setTypeface(null,Typeface.NORMAL)
            drinks.setTypeface(null,Typeface.NORMAL)
            mainCourses.setTypeface(null,Typeface.BOLD)
            desserts.setTypeface(null,Typeface.NORMAL)
            category == "Huvudrätt"

        }

        desserts.setOnClickListener {
            starters.setTypeface(null,Typeface.NORMAL)
            drinks.setTypeface(null,Typeface.NORMAL)
            mainCourses.setTypeface(null,Typeface.NORMAL)
            desserts.setTypeface(null,Typeface.BOLD)
        }


        drinks.setOnClickListener {
            drinks.setTypeface(null, Typeface.BOLD)


            val intent = Intent(this,DrinksViewActivity::class.java)
            intent.putExtra("restId", restaurantid)
            this.startActivity(intent)
        }



        fab_add_dish.setOnClickListener{
            val intent = Intent(this, AddNChangeFoodActivity::class.java)

            intent.putExtra("resid", restaurantid)


            startActivity(intent)


        }




        val backButton = findViewById<ImageView>(R.id.foodViewBackButton)
        backButton.setOnClickListener{
            finish()
        }

        val homeButton = findViewById<ImageView>(R.id.ibtn_home_drinksView)
        homeButton.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            this.startActivity(intent)
        }
        val logo = findViewById<ImageView>(R.id.iv_Logo)
        logo.setOnClickListener{
            val intent= Intent(this,RestaurantViewActiviity::class.java)
            startActivity(intent)
        }










    }






    override fun onResume() {
        super.onResume()



        foodRecyclerView.adapter?.notifyDataSetChanged()




    }

    override fun OnClick(position: Int) {



            val n =2
            val userId = restaurantid.dropLast(n)

            if (userUID == userId){

                val intent = Intent(this, AddNChangeFoodActivity::class.java)


                intent.putExtra("resid", restaurantid)
                intent.putExtra(DISH_POSTION_KEY, position)



                Log.d("LLL",restaurantid)

                startActivity(intent)
            }


}


}