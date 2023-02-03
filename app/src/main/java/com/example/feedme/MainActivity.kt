package com.example.feedme

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.feedme.data.User
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

val db = Firebase.firestore




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mockUserData()
    }

    private fun mockUserData() {
        //Create new restaurant object
        val user1 = User("Olof",
            "Svensson","Storgatan 95", "Storstan",
            "54648", "944237524", "olof.svensson@svensson.nu","administrator")
        val user2 = User("Lisa",
            "Ahl","Lilla vägen 5", "byn",
            "56343", "2343435", "lisa.ahl@ahl.com", "customer")
        val user3 = User("Lina",
            "Green","Stora vägen 27", "Göteborg",
            "45698", "234554346", "Lina.green@green.com", "customer")
        val user4 = User("Anna",
            "Anderson","Norra gatan 1", "Göteborg",
            "45632", "62354634", "anna.andersson@andersson.nu", "delivery")

        //Add it to collection restaurants, SetOptions.merge() = do not overwrite if exists
        db.collection("users").document("user1").set(user1, SetOptions.merge())
        db.collection("users").document("user2").set(user2, SetOptions.merge())
        db.collection("users").document("user3").set(user3, SetOptions.merge())
        db.collection("users").document("user4").set(user4, SetOptions.merge())

    }
}