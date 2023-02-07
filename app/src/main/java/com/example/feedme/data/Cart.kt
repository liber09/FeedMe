package com.example.feedme.data

data class Cart(
    var rowCount:Int = 0,
    val rowTitle: String = "",
    val rowAllergen: String = "",
    var rowPrice: Double = 0.0,
    val rowIsGlutenFree: Boolean = false,
    val rowIslactoseFree: Boolean = false,
    var rowIsEggFree: Boolean = false,
    var rowIsNutfree: Boolean= false,
    var rowIsFreeFromSeeFood: Boolean = false,
    var rowIsSoyfree: Boolean = false
)