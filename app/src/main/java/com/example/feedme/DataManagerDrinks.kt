package com.example.feedme
import Drink
import com.google.firebase.firestore.SetOptions

object DataManagerDrinks{
    var drinksList:MutableList<Drink> = arrayListOf()

    val drink1 = Drink(
        "",
        "Coca Cola",
        "33cl",
        16.0,
        "Soda",
        )
    val drink2 = Drink(
        "",
        "Coca Cola light",
        "33cl",
        16.0,
        "Soda",
    )
    val drink3 = Drink(
        "",
        "Coca Cola Zero",
        "33cl",
        16.0,
        "Soda",
    )
    val drink4 = Drink(
        "",
        "Pepsi",
        "33cl",
        16.0,
        "Soda",
    )
    val drink5 = Drink(
        "",
        "Pepsi Max",
        "33cl",
        16.0,
        "Soda",
    )
    val drink6 = Drink(
        "",
        "Fanta",
        "33cl",
        16.0,
        "Soda",
    )
    val drink7 = Drink(
        "",
        "Fanta light",
        "33cl",
        16.0,
        "Soda",
    )
    val drink8 = Drink(
        "",
        "Sprite",
        "33cl",
        16.0,
        "Soda",
    )
    val drink9 = Drink(
        "",
        "Sprite Zero",
        "33cl",
        16.0,
        "Soda",
    )
    val drink10 = Drink(
        "",
        "Coca Cola",
        "50cl",
        16.0,
        "Soda",
    )
    val drink11 = Drink(
        "",
        "Coca Cola light",
        "50cl",
        16.0,
        "Soda",
    )
    val drink12 = Drink(
        "",
        "Coca Cola Zero",
        "50cl",
        16.0,
        "Soda",
    )
    val drink13 = Drink(
        "",
        "Pepsi",
        "50cl",
        16.0,
        "Soda",
    )
    val drink14 = Drink(
        "",
        "Pepsi Max",
        "50cl",
        16.0,
        "Soda",
    )
    val drink15 = Drink(
        "",
        "Fanta",
        "50cl",
        16.0,
        "Soda",
    )
    val drink16 = Drink(
        "",
        "Fanta light",
        "50cl",
        16.0,
        "Soda",
    )
    val drink17 = Drink(
        "",
        "Sprite",
        "50cl",
        16.0,
        "Soda",
    )
    val drink18 = Drink(
        "",
        "Sprite Zero",
        "50cl",
        16.0,
        "Soda",
    )
    val drink19 = Drink(
        "",
        "Santa Helena chardonnay",
        "75cl",
        125.0,
        "White wine",
    )
    val drink20 = Drink(
        "",
        "Byron bay sauvignon blanc",
        "75cl",
        225.0,
        "White wine",
    )
    val drink21 = Drink(
        "",
        "Gran Reserva rioja anciano",
        "75cl",
        121.0,
        "Red wine",
    )
    val drink22 = Drink(
        "",
        "Barolo massimo rivetti",
        "75cl",
        175.0,
        "Red wine",
    )
    val drink23 = Drink(
        "",
        "Heinekken",
        "33cl",
        65.0,
        "Beer",
    )
    val drink24 = Drink(
        "",
        "Norrlands Guld",
        "33cl",
        48.0,
        "Beer",
    )
    val drink25 = Drink(
        "",
        "Heinekken",
        "50cl",
        78.0,
        "Beer",
    )
    val drink26 = Drink(
        "",
        "Norrlands Guld",
        "50cl",
        60.0,
        "Beer",
    )
    val drink27 = Drink(
        "",
        "Ramlösa",
        "33cl",
        18.0,
        "Soda water",
    )
    val drink28 = Drink(
        "",
        "Imsdal",
        "50cl",
        27.0,
        "Water",
    )

    private fun createMockDataDrinks(){
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink1)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink2)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink3)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink4)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink5)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink6)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink7)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink8)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink9)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink10)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink11)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink12)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink13)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink14)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink15)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink16)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink17)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink18)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink19)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink20)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink21)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink22)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink23)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink24)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink25)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink26)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink27)
        db.collection("restaurants").document("restaurant2").collection("drinks").add(drink28)
    }



}
