package com.example.feedme

data class Dishes(
    var title: String? = null,
    var description: String? = null,
    var priceNormalPortion: Double? = null,
    var priceSmallPortion: Double? = null,
    var priceLargePortion: Double? = null,
    var containsGluten: Boolean = true,
    var containsLaktose: Boolean = true,
    var containsEggs: Boolean = true,
    var containsNuts: Boolean= true,
    var containsSeeFood: Boolean = true,
    var containsSoy: Boolean = true,
   // var isLCHF: Boolean = true,
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



    var dishImagePath :String = "",
  //  @DocumentId var documentId : String? = null,

)
