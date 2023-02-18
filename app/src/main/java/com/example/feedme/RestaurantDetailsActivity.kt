package com.example.feedme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.feedme.data.Dishes
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

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
            val restaurantImage = findViewById<ImageView>(R.id.imgRestaurant)
            if(id == restaurant.documentId){
                restaurantTitel.text = restaurant.name
                restaurantdescripton.text = restaurant.description

                //Get the image from firebase
                if(restaurant.imagePath.isNotEmpty()) {
                    val imageref = Firebase.storage.reference.child(restaurant.imagePath)
                    imageref.downloadUrl.addOnSuccessListener { Uri ->
                        val imageURL = Uri.toString() // get the URL for the image
                        //Use third party product glide to load the image into the imageview
                        Glide.with(this)
                            .load(imageURL)
                            .into(restaurantImage)
                    }
                }


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