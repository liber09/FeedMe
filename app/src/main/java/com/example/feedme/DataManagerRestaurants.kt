package com.example.feedme

import com.example.feedme.data.Restaurant

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
}