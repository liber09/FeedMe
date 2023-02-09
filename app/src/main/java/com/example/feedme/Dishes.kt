package com.example.feedme

import com.google.firebase.firestore.DocumentId

data class Dishes(
    var title: String? = null,
    var description: String? = null,
    var priceNormalPortion: Double? = null,
    var priceSmallPortion: Double? = null,
    var priceLargePortion: Double? = null,
    var isGlutenFree: Boolean = false,
    var isLaktoseFree: Boolean = false,
    var isEggFree: Boolean = false,
    var isNutfree: Boolean= false,
    var isFreeFromSeeFood: Boolean = false,
    var isSoyfree: Boolean = false,
    var isLCHF: Boolean = false,
    var isVegetarian: Boolean = false,
    var isVegan: Boolean = false,
    var category: String = "Huvudrätt", // dropdownfält?
    var canBeMadeGlutenFree: Boolean = false,
    var canBeMadeLaktosFree: Boolean = false,
    var canBeMadeVegan: Boolean = false,
    var canBeMadeVegeterian: Boolean = false,
    var extraCostGluten: Double? = null,
    var extraCostLaktose: Double? = null,
    var extraCostVegan: Double? = null,
    var extraCostVegeterian: Double? = null,
    var selectedFoodSize : String = "",
    var dishImagePath :String = "",
    @DocumentId var documentId : String? = null,

    )
