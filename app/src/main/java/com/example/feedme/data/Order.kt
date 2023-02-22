package com.example.feedme.data

import Drink
import com.google.firebase.firestore.DocumentId
import com.google.type.DateTime
import java.time.LocalDate

data class Order (
    @DocumentId var restaurantDocumentId : String? = null,
    @DocumentId var customerId:String? = null,
    @DocumentId var orderId: String? = null,
    var orderedDishes: MutableList<Dishes>?,
    var orderedDrinks: MutableList<Drink>?,
    var orderDate: LocalDate? = null,
    var orderNr:Int? = null,
    var totalAmount:Double? = null,
    var typeOfDelivery:String? = null
    )