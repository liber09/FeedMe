package com.example.feedme.data

import com.google.firebase.firestore.DocumentId

data class DeliveryPerson(
    val firstName: String = "",
    val lastName: String = "",
    val employer: String = "",
    val city: String = "",
    val postalCode: String = "",
    val phoneNumber: String = "",
    val email: String="",
    val typeOfUser: String = "delivery", //customer, administrator, delivery
@DocumentId var documentId : String? = null,

)
