package com.example.feedme.data

import java.security.KeyStore.TrustedCertificateEntry

data class restaurant (
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
    val tableBooking: Boolean = false
)
