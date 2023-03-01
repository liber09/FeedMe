package com.example.feedme


import Drink
import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.feedme.data.*

import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.toObject

val db = Firebase.firestore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Create mock data
        mockCustomerData()
        //mockRestaurantData()
        //mockDataDrinks()


        val sc = findViewById<Button>(R.id.btn_Shopping)

        val ra = findViewById<Button>(R.id.buttonRegister)


        val ci = findViewById<Button>(R.id.btn_customerInfo)
        val rov = findViewById<Button>(R.id.btnOrderView)
        val getOrders = findViewById<Button>(R.id.btnGetOrders)

        val menu = findViewById<Button>(R.id.menuBtn)



        val tvresId = findViewById<EditText>(R.id.resNrEdtTxt )
        val restv = findViewById<TextView>(R.id.btnRestaurantList)


        restv.setOnClickListener{
            val intent= Intent(this,RestaurantViewActiviity::class.java)
            startActivity(intent)
        }

        menu.setOnClickListener{
            val intent= Intent(this,MenuActivity::class.java)
            startActivity(intent)

        }



        getOrders.setOnClickListener{
            val restaurantId = tvresId.text.toString()
            getOrdersForRestaurant(restaurantId)
        }

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


        /*rr.setOnClickListener {    val intent= Intent(this,InfoRestaurantActivity::class.java)
            intent.putExtra("RESTAURANT_KEY",1)
            startActivity(intent) }*/



        ra.setOnClickListener{
            val intent= Intent(this,LoginAndRegisterActivity::class.java)
            startActivity(intent)
        }

        //val infoRes = findViewById<Button>(R.id.btn_infoRes)





        // TODO THIS below
        //  here we need to get the intent from the restaurant
        //  RecyclerView for the documentpath as soon as that is
        //  fixed så we can put the extra in the documentPaht


        val drinkRef = db.collection("restaurants").document("restaurant2")
            .collection("drinks")    // Drink ref from database collection
        drinkRef.addSnapshotListener { snapshot, e ->
            if (snapshot != null) {

                DataManagerDrinks.drinkList.clear()
                for (document in snapshot.documents) {
                    val item = document.toObject<Drink>()
                    //Get parent documentId - restaurant in this case
                    item?.restaurantDocumentId = document.reference.parent.parent?.id.toString()
                    if (item != null) {
                        DataManagerDrinks.drinkList.add(item)
                    }
                }

                printDrinks()
            }
        }

        val docRef =db.collection("restaurants").document("restaurant2").collection("dishes")
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

                //printDishes()
            }
        }


        val restaurantRef = db.collection("restaurants")
        restaurantRef.addSnapshotListener{ snapshot, e ->
            if (snapshot != null) {
                DataManagerRestaurants.restaurants.clear()
                for (document in snapshot.documents){
                    if (document != null) {
                        document.toObject<Restaurant>()
                            ?.let { DataManagerRestaurants.restaurants.add(it) }
                    }
                }

                printRestaurants()
            }
        }

        val customersRef = db.collection("customers")
        customersRef.addSnapshotListener{ snapshot, e ->
            if (snapshot != null) {
                DataManagerCustomers.customers.clear()
                for (document in snapshot.documents){
                    if (document != null) {
                        document.toObject<Customer>()
                            ?.let { DataManagerCustomers.customers.add(it) }
            }
        }
                getOrdersForRestaurant("IDMaTpuYDSZKKO688AcNlshdggH2+1")
    }
}


/*
val ordersRef = db.collection("orders")
ordersRef.addSnapshotListener{ snapshot, e ->
    if (snapshot != null) {
        DataManagerOrders.orders.clear()
        for (document in snapshot.documents){
            if (document != null) {
                document.toObject<Order>()
                    ?.let { DataManagerOrders.orders.add(it) }
            }
        }
    }
}
*/
}



// Declare the launcher at the top of your Activity/Fragment:
private val requestPermissionLauncher = registerForActivityResult(
ActivityResultContracts.RequestPermission()
) { isGranted: Boolean ->
if (isGranted) {
    // FCM SDK (and your app) can post notifications.
} else {
    // TODO: Inform user that that your app will not show notifications.
}
}

private fun askNotificationPermission() {
// This is only necessary for API level >= 33 (TIRAMISU)
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY) ==
        PackageManager.PERMISSION_GRANTED
    ) {
        // FCM SDK (and your app) can post notifications.
    } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {
        // TODO: display an educational UI explaining to the user the features that will be enabled
        //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
        //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
        //       If the user selects "No thanks," allow the user to continue without notifications.
    } else {
        // Directly ask for the permission
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_NOTIFICATION_POLICY)
    }
}
}

//Get orders for restaurant with id restaurantId
//Clear ordersList and add the ones returned from the query.
fun getOrdersForRestaurant(restaurantId: String){
DataManagerOrders.orders.clear()
var index = 0
db.collection("restaurants").document(restaurantId).collection("orders")
    .get()
    .addOnSuccessListener { documents ->
        for (document in documents) {
            DataManagerOrders.orders.add(document.toObject())
            db.collection("restaurants").document(restaurantId).collection("orders").document(document.id).collection("orderedDishes")
                .get()
                .addOnSuccessListener { orderItems ->
                    for(orderItem in orderItems) {
                        DataManagerOrders.orders.get(index).orderedDishes.add(orderItem.toObject<OrderItem>())
                        Log.d(TAG, "${orderItem.id} => ${orderItem.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
            Log.d(TAG, "${document.id} => ${document.data}")
        }
    }
    .addOnFailureListener { exception ->
        Log.w(TAG, "Error getting documents: ", exception)
    }
}


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
    fun printDrinks() {

        for (item in DataManagerDrinks.drinkList) {
            Log.d("HHH", "${item.drinkName}")

        }
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
/*
private fun createMockDataOrders() {§
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
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val current = LocalDateTime.now().format(formatter)

val order1 = Order("restaurant2","Customer1","Order1",current, 1, "345983533",150.0, "Home"
)
db.collection("orders").add(order1)
//dAb.collection("orders").document("order1").collection("orderDrinks").add(orderDrink2)
//db.collection("orders").document("order1").collection("orderDishes").add(orderDish1)
}
*/
/*    fun getCustomerByDocumentId(customerId: String):Customer?{
var listOfCustomers = MutableList<Customer>()

var customer: Customer? = null
val documentref = db.collection("customers")
documentref.addSnapshotListener{ snapshot, e ->
    if (snapshot != null) {
        for (document in snapshot.documents)
        { val item = document.toObject<Customer>()
            if (item != null) {
                listOfCustomers.add(item)
            }
        }
    }
}
for (cust in listOfCustomers){
    if (cust.customerId== customerId){
        customer = cust
    }
}
return customer
}*/

}
