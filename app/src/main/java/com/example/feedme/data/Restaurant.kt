package com.example.feedme.data

import com.google.firebase.firestore.DocumentId
import java.util.*
import kotlin.collections.HashMap

data class Restaurant(
    val name: String = "",
    val orgNr: String = "",
    val address: String = "",
    val postalCode: String = "",
    val city: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val type: String = "",
    val deliveryFee: Int = 0,
    val deliveryTypePickup: Boolean = true,
    val deliveryTypeHome: Boolean = false,
    val deliveryTypeAtRestaurant: Boolean = true,
    val tableBooking: Boolean = false,
    val description:String = "",
    val rating:Double?= null,
    val imagePath:String ="",
    var documentInternal: String?=null,
    @DocumentId val documentId: String?=null,
    val openingHours: HashMap<String, Date> = hashMapOf<String, Date>()
)
