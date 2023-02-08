package com.example.feedme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.feedme.data.Restaurant

import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore



class InfoRestaurantActivity : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_restaurant)

        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnAddImage = findViewById<Button>(R.id.btn_add_image)

        btnAddImage.setOnClickListener {

        }

        btnSave.setOnClickListener {
            val name = findViewById<EditText>(R.id.textInputName).text.toString()

            val rest = Restaurant(
                name,
                "",
                findViewById<EditText>(R.id.textInputAddress).text.toString(),
                findViewById<EditText>(R.id.textInputPostalCode).text.toString(),
                findViewById<EditText>(R.id.textInputCity).text.toString(),
                findViewById<EditText>(R.id.textInputPhone).text.toString(),
                findViewById<EditText>(R.id.textInputEmail).text.toString(),
                "",
                findViewById<EditText>(R.id.textInputDeliveryPrice).text.toString().toInt(),
                findViewById<CheckBox>(R.id.cb_takeaway).isChecked,
                findViewById<CheckBox>(R.id.cb_homeDelivery).isChecked,
                findViewById<CheckBox>(R.id.cb_atRestaurant).isChecked,
                findViewById<CheckBox>(R.id.cb_tableBooking).isChecked,
            )

            db.collection("restaurantTibTest").document(name)
                .set(rest)
        }
    }
}