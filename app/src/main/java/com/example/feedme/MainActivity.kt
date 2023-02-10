package com.example.feedme


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.feedme.data.Customer
import com.example.feedme.data.Dishes
import com.example.feedme.data.Restaurant
import com.google.firebase.firestore.ktx.toObject

val db = Firebase.firestore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Create mock data
        mockCustomerData()
        mockRestaurantData()

        val add = findViewById<Button>(R.id.btn_add_act)
        val rv = findViewById<Button>(R.id.btn_RV_act)
        val sc = findViewById<Button>(R.id.btn_Shopping)
        val rr = findViewById<Button>(R.id.btn_RegREst)
        val ru = findViewById<Button>(R.id.btn_Marlon)
        val ra = findViewById<Button>(R.id.buttonRegister)
        val bv = findViewById<Button>(R.id.btn_budView)
        val mv = findViewById<Button>(R.id.btn_toMapsDel)
        val ci = findViewById<Button>(R.id.btn_customerInfo)
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

       /* infoRes.setOnClickListener {
            val intent = Intent(this, InfoRestaurantActivity::class.java)

            startActivity(intent)
        } */


        val docRef =db.collection("restaurants").document("restaurant2").collection("dishes")
        docRef.addSnapshotListener{ snapshot, e ->
            if (snapshot != null) {
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
        }
        val restaurantRef = db.collection("restaurants")
        restaurantRef.addSnapshotListener{ snapshot, e ->
            if (snapshot != null) {
                for (document in snapshot.documents)
                { val item = document.toObject<Restaurant>()
                    if (item != null) {
                        DataManagerRestaurants.restaurants.add(item)
                    }
                }

                printDishes()
            }
        }







    }

    //Mock restaurant data, create 4 restaurants and push to DB
    private fun mockRestaurantData() {
        val restaurant1 = Restaurant(
            "Karlbergs Krog","5454-5454","Västanvindsgatan 1","44454",
            "Stenungsund","030323654","info@karlbergs.se","Husmanskost",
            50,true,true,true,
            false)
        val restaurant2 = Restaurant(
            "Restaurant 2012","5580-5465","Storgatan 12","56234",
            "Göteborg","0315648958","info@restaurant2012.se","Italienskt",
            45,true,false,true,
            true)
        val restaurant3 = Restaurant(
            "Jamie Oliver's gardens","5555-5454","Fancy Pancy street 15","12345",
            "Posh city","254685478","info@jamieoliver.com","Ala carté",
            150,true,true,true,
            true)
        val restaurant4 = Restaurant(
            "King Charles III","6555-5454","Buckingham palace 1","56458",
            "London","45213658","info@buckingham.co.uk","Ala carté",
            100,true,true,true,
            false)

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
    fun  printDishes(){

        for (item in DataManagerDishes.dishes)
        {
            Log.d("HHH", "${item.title}")

        }


    }
}
