package com.example.feedme


import Drink
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.feedme.data.Customer
import com.example.feedme.data.Dishes
import com.example.feedme.data.Order
import com.example.feedme.data.Restaurant
import com.google.firebase.firestore.ktx.toObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val db = Firebase.firestore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Create mock data
        mockCustomerData()
        mockRestaurantData()
        //mockDataDrinks()
       //createMockDataOrders()

        val add = findViewById<Button>(R.id.btn_add_act)
        val rv = findViewById<Button>(R.id.btn_RV_act)
        val sc = findViewById<Button>(R.id.btn_Shopping)
        val rr = findViewById<Button>(R.id.btn_RegREst)
        val ru = findViewById<Button>(R.id.btn_Marlon)
        val ra = findViewById<Button>(R.id.buttonRegister)
        val bv = findViewById<Button>(R.id.btn_budView)
        val mv = findViewById<Button>(R.id.btn_toMapsDel)
        val ci = findViewById<Button>(R.id.btn_customerInfo)
        val rov = findViewById<Button>(R.id.btnOrderView)
        val rrv = findViewById<Button>(R.id.btnRestview)
        val getOrders = findViewById<Button>(R.id.btnGetOrders)
        val btnDrinks = findViewById<Button>(R.id.btnDrinks)

        val tvresId = findViewById<EditText>(R.id.resNrEdtTxt )
        val restv = findViewById<TextView>(R.id.btnREST)

        btnDrinks.setOnClickListener{
            val intent = Intent(this,DrinksViewActivity::class.java)
            startActivity(intent)
        }

        restv.setOnClickListener{
            val intent= Intent(this,RestaurantViewActiviity::class.java)
            startActivity(intent)

        }




        getOrders.setOnClickListener{
            val restaurantId = tvresId.text.toString()
            getOrdersForRestaurant(restaurantId)
        }

        rrv.setOnClickListener {     val intent= Intent(this,DrinksViewActivity::class.java)
            startActivity(intent) }

        rov.setOnClickListener{
            val intent= Intent(this,OrderViewForRestaurants::class.java)
            startActivity(intent)
        }
        ci.setOnClickListener{
            val intent= Intent(this,RegisterCustomerInfo::class.java)
            startActivity(intent)
        }

        sc.setOnClickListener{
            val intent= Intent(this,ShoppingCart::class.java)
            startActivity(intent)
        }
        ru.setOnClickListener {    val intent= Intent(this,RegisterActivity::class.java)
            startActivity(intent) }

        rr.setOnClickListener {    val intent= Intent(this,InfoRestaurantActivity::class.java)
            startActivity(intent) }


        bv.setOnClickListener{
            val intent= Intent(this,DeliveryPersonViewActivity::class.java)
            startActivity(intent)

        }
        mv.setOnClickListener{
            val intent= Intent(this,DeliveryMapsActivity::class.java)
            startActivity(intent)

        }


        ra.setOnClickListener{
            val intent= Intent(this,LoginAndRegisterActivity::class.java)
            startActivity(intent)
        }

        //val infoRes = findViewById<Button>(R.id.btn_infoRes)

        add.setOnClickListener {
            val intent = Intent(this, AddNChangeFoodActivity::class.java)
            startActivity(intent)
        }
        rv.setOnClickListener {
            val intent = Intent(this, FoodViewActivity::class.java)

            startActivity(intent)
        }


        // TODO THIS below
        //  here we need to get the intent from the restaurant
        //  RecyclerView for the documentpath as soon as that is
        //  fixed så we can put the extra in the documentPaht




      /*  val docRef =db.collection("restaurants").document("restaurant2").collection("dishes")
        docRef.addSnapshotListener{ snapshot, e ->
            if (snapshot != null) {

                DataManagerDishes.dishes.clear()
                for (document in snapshot.documents)

                { val item = document.toObject<Dishes>()
                    //Get parent documentId - restaurant in this case
                    item?.restaurantDocumentId = document.reference.parent.parent?.id.toString()
                    if (item != null) {
                        DataManagerDishes.dishes.add(item)
                    }
                }

                printDishes()
            }
        }*/


        val restaurantRef = db.collection("restaurants")
        restaurantRef.addSnapshotListener{ snapshot, e ->
            if (snapshot != null) {
                DataManagerRestaurants.restaurants.clear()

                for (document in snapshot.documents)
                { val item = document.toObject<Restaurant>()
                    if (item != null) {
                        DataManagerRestaurants.restaurants.add(item)
                    }
                }

                printRestaurants()
            }
        }









    }
    //Get orders for restaurant with id restaurantId
    //Clear ordersList and add the ones returned from the query.
    fun getOrdersForRestaurant(restaurantId: String){
        DataManagerOrders.orders.clear()
        db.collection("orders")
            .whereEqualTo("restaurantId", restaurantId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    DataManagerOrders.orders.add(document.toObject<Order>())
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

   /*private fun mockDataDrinks() {
       val drinkList = mutableListOf<Drink>()

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
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink1
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink2
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink3
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink4
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink5
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink6
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink7
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink8
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink9
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink10
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink11
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink12
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink13
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink14
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink15
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink16
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink17
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink18
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink19
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink20
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink21
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink22
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink23
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink24
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink25
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink26
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink27
       )
       db.collection("restaurants").document("restaurant2").collection("drinks").add(
           drink28
       )
   }*/


    //Mock restaurant data, create 4 restaurants and push to DB
    private fun mockRestaurantData() {
        val restaurant1 = Restaurant(
            "Karlbergs Krog","5454-5454","Västanvindsgatan 1","44454",
            "Stenungsund","030323654","info@karlbergs.se","Husmanskost",
            50,true,true,true,
            false,
            "Traditionell svenskt husmanskost och mysig miljö väntar våra gäster hos oss",5.0)
        val restaurant2 = Restaurant(
            "Restaurant 2012","5580-5465","Storgatan 12","56234",
            "Göteborg","0315648958","info@restaurant2012.se","Italienskt",
            45,true,false,true,
            true,"Trevlig ambiente möter hip matlagning. Du har manbun och skägg - varmt välkommen",4.4)
        val restaurant3 = Restaurant(
            "Jamie Oliver's gardens","5555-5454","Fancy Pancy street 15","12345",
            "Posh city","254685478","info@jamieoliver.com","Ala carté",
            150,true,true,true,
            true,"Smaka på Jamies favoritrecepter",3.8)
        val restaurant4 = Restaurant(
            "King Charles III","6555-5454","Buckingham palace 1","56458",
            "London","45213658","info@buckingham.co.uk","Ala carté",
            100,true,true,true,
            false,"Vårt motto: Posh, posh, posh men så mumsig med",4.2)
        //Add restaurants to collection restaurants, SetOptions.merge() = do not overwrite if exists
        db.collection("restaurants").document("restaurant1").set(restaurant1, SetOptions.merge())
        db.collection("restaurants").document("restaurant2").set(restaurant2, SetOptions.merge())
        db.collection("restaurants").document("restaurant3").set(restaurant3, SetOptions.merge())
        db.collection("restaurants").document("restaurant4").set(restaurant4, SetOptions.merge())
    }
    //Mock user data, creates 4 users and push to DB
    private fun mockCustomerData() {
        //Create new customer object
        val customer1 = Customer("Olof",
            "Svensson","Storgatan 95", "Storstan",
            "54648", "944237524", "olof.svensson@svensson.nu","administrator",
            "Fisk, skaldjur, ägg","olle_svenne")
        val customer2 = Customer("Lisa",
            "Ahl","Lilla vägen 5", "byn",
            "56343", "2343435", "lisa.ahl@ahl.com", "customer",
            "","Liiiisa")
        val customer3 = Customer("Lina",
            "Green","Stora vägen 27", "Göteborg",
            "45698", "234554346", "Lina.green@green.com", "customer",
            "Äcklig mat","Greniz")
        val customer4 = Customer("Anna",
            "Anderson","Norra gatan 1", "Göteborg",
            "45632", "62354634", "anna.andersson@andersson.nu", "delivery",
            "Gluten, laktos", "aannndae")

        //Add it to collection restaurants, SetOptions.merge() = do not overwrite if exists
        db.collection("customers").document("customer1").set(customer1, SetOptions.merge())
        db.collection("customers").document("customer2").set(customer2, SetOptions.merge())
        db.collection("customers").document("customer3").set(customer3, SetOptions.merge())
        db.collection("customers").document("customer4").set(customer4, SetOptions.merge())

    }
  /*  fun  printDishes(){

        for (item in DataManagerDishes.dishes)
        {
            Log.d("HHH", "${item.title}")

        }


    }*/

    private fun printRestaurants() {
        for (item in DataManagerRestaurants.restaurants)
        {
            Log.d("HHH", "${item.name}")

        }


    }

    private fun createMockDataOrders() {
        var orderDishes = mutableListOf<Dishes>()
        var orderDrinks = mutableListOf<Drink>()

        val orderDrink2 = Drink(
            "",
            "Coca Cola light",
            "33cl",
            16.0,
            "Soda",
        )
        val orderDish1 = Dishes(
            "Spagetti Bolonese","Smakrik köttfärssås",88.0,75.0,95.0,false,
            false,false,false,true,false,false,false,false,"Huvudrätt",true,
            false,false,true,10.0,10.0,10.0,10.0,"N",
            "",0, "","Restaurant2"
        )
        orderDishes.add(orderDish1)
        orderDrinks.add(orderDrink2)
        val localDate = LocalDate.parse("2023-01-06", DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val order1 = Order(
            "restaurant2","Customer1","Order1",orderDishes, orderDrinks,localDate,1,100.0,"Home"
        )
        db.collection("orders").add(order1)
        db.collection("orders").document("order1").collection("orderDrinks").add(orderDrink2)
        db.collection("orders").document("order1").collection("orderDishes").add(orderDish1)
    }
}
