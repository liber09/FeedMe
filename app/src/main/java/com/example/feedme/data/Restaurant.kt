package com.example.feedme.data

import com.google.firebase.firestore.DocumentId
import java.util.*
import kotlin.collections.HashMap

data class Restaurant (
    val name: String = "",
    val orgNr: String = "",
    val address: String = "",
    val postalCode: String = "",
    val city: String = "",
    val phoneNumber: String = "",
    val eMail: String = "",
    val type: String = "",
    val deliveryFee: Int = 0,
    val deliveryTypePickup: Boolean = true,
    val deliveryTypeHome: Boolean = false,
    val deliveryTypeAtRestaurant: Boolean = true,
    val tableBooking: Boolean = false,
    @DocumentId var documentId : String? = null,
    val openingHours: HashMap<String, Calendar> = hashMapOf<String, Calendar>(),
    val description: String = ""
)
