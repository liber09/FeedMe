package com.example.feedme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.feedme.data.Dishes
import com.google.firebase.firestore.ktx.toObject

class RestaurantDetailsActivity : AppCompatActivity() {

    lateinit var restaurantTitel :TextView
    lateinit var restaurantdescripton :TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)
        val intent:Intent = getIntent()
        val id = intent.getStringExtra("id")

        restaurantTitel = findViewById(R.id.tv_restTitle_details)
        restaurantdescripton = findViewById(R.id.tv_Rest_Descript_RestDetails)
        val menueButton = findViewById<Button>(R.id.btn_menu)

        for (restaurant in DataManagerRestaurants.restaurants){

            if(id == restaurant.documentId){
                restaurantTitel.text = restaurant.name
                restaurantdescripton.text = restaurant.description


                val docRef =db.collection("restaurants").document(id!!).collection("dishes")
                docRef.addSnapshotListener{ snapshot, e ->
                    if (snapshot != null) {

                        DataManagerDishes.dishes.clear()
                        for (document in snapshot.documents)

                        { val item = document.toObject<Dishes>()
                            //Get parent documentId - restaurant in this case
                            item?.restaurantDocumentId = document.reference.parent.parent?.id.toString()
                            if (item != null) {
                                DataManagerDishes.dishes.add(item)
                            }
                        }

                        printDishes()
                    }
                }




                menueButton.setOnClickListener {

                    val intent= Intent(this,FoodViewActivity::class.java)
                    intent.putExtra("id", id)


                    startActivity(intent)


                }



            }


        }



    }
    fun  printDishes(){

        for (item in DataManagerDishes.dishes)
        {
            Log.d("HHH", "${item.title}")

        }


    }



}