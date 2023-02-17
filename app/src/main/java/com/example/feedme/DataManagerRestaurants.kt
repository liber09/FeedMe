package com.example.feedme

import android.util.Log
import com.example.feedme.data.Restaurant
import com.google.firebase.firestore.ktx.toObject

object DataManagerRestaurants {
    val restaurants = mutableListOf<Restaurant>()

    fun getByDocumentId(documentId:String): Restaurant? {

        for(restaurant in restaurants){
            if(restaurant.documentId.equals(documentId)){
                return restaurant
            }
        }
        return null
    }

    fun update() {
        restaurants.clear()
        db.collection("restaurants")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("GET_RESTAURANTS", "${document.id} => ${document.data}")
                    restaurants.add(document.toObject<Restaurant>())
                }
            }
            .addOnFailureListener { exception ->
                Log.w("GET_RESTAURANTS", "Error getting documents: ", exception)
            }
    }
}