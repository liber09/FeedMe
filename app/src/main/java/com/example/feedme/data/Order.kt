package com.example.feedme.data

import Drink
import com.google.firebase.firestore.DocumentId
import com.google.type.DateTime
import java.time.LocalDate

data class Order (
    @DocumentId var restaurantDocumentId : String? = null,
    @DocumentId var customerId:String? = null,
    @DocumentId var orderId: String? = null,
    var orderedDishes: MutableList<Dishes>? = null,
    //var orderedDrinks: MutableList<Drink>?,
    var orderDate: String? = null,
    var orderNr:Int? = null,
    var customerPhoneNumber: String? = null,
    var totalAmount:Double? = null,
    var typeOfDelivery:String? = null,
    var customerNumber: Int? = null
    )