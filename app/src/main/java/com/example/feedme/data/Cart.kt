package com.example.feedme.data

data class Cart(
    var rowCount:Int = 0,
    val rowTitle: String = "",
    val rowAllergen: String = "",
    var rowPrice: Double = 0.0
)