package com.example.feedme.data

import com.google.firebase.firestore.DocumentId

data class  Customer (
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val city: String = "",
    val postalCode: String = "",
    val phoneNumber: String = "",
    val eMail: String = "",
    val typeOfUser: String = "", //customer, administrator, delivery
    val allergies: String = "",
    val userName: String = "",
    val customerNumber: String = "",
@DocumentId var customerId:String? = null,
)

