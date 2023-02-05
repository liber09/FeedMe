package com.example.feedme


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.feedme.data.User
import com.example.feedme.data.Restaurant

val db = Firebase.firestore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mockUserData()
        mockRestaurantData()

        val add = findViewById<Button>(R.id.btn_add_act)
        val rv = findViewById<Button>(R.id.btn_RV_act)

        add.setOnClickListener {
            val intent = Intent(this, AddNChangeFoodActivity::class.java)

            startActivity(intent)

        }
        rv.setOnClickListener {
            val intent = Intent(this, FoodViewActivity::class.java)

            startActivity(intent)
        }


        mockUserData()
        mockRestaurantData()

        val test = findViewById<Button>(R.id.button7)

        test.setOnClickListener{
            val register = Intent(this,RegisterCustomerInfo::class.java)
            startActivity(register)
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
    private fun mockUserData() {
        //Create new restaurant object
        val user1 = User("Olof",
            "Svensson","Storgatan 95", "Storstan",
            "54648", "944237524", "olof.svensson@svensson.nu","administrator",
            "Fisk, skaldjur, ägg","olle_svenne")
        val user2 = User("Lisa",
            "Ahl","Lilla vägen 5", "byn",
            "56343", "2343435", "lisa.ahl@ahl.com", "customer",
            "","Liiiisa")
        val user3 = User("Lina",
            "Green","Stora vägen 27", "Göteborg",
            "45698", "234554346", "Lina.green@green.com", "customer",
            "Äcklig mat","Greniz")
        val user4 = User("Anna",
            "Anderson","Norra gatan 1", "Göteborg",
            "45632", "62354634", "anna.andersson@andersson.nu", "delivery",
            "Gluten, laktos", "aannndae")

        //Add it to collection restaurants, SetOptions.merge() = do not overwrite if exists
        db.collection("users").document("user1").set(user1, SetOptions.merge())
        db.collection("users").document("user2").set(user2, SetOptions.merge())
        db.collection("users").document("user3").set(user3, SetOptions.merge())
        db.collection("users").document("user4").set(user4, SetOptions.merge())

    }
}
