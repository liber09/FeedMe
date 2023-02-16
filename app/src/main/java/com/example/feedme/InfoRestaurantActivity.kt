package com.example.feedme

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import com.example.feedme.data.Restaurant
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class InfoRestaurantActivity : AppCompatActivity() {

    val db = Firebase.firestore
    private var openingHours = hashMapOf<String, Date>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_restaurant)

        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnAddImage = findViewById<Button>(R.id.btn_add_image)
        if(intent.hasExtra("RESTAURANT_KEY")) {
            val rest = DataManagerRestaurants.getByDocumentId(intent.getStringExtra("RESTAURANT_KEY") ?: "1")
            loadRestaurant(rest ?: Restaurant())
        }

        //loadRestaurant(DataManagerRestaurants.restaurants.get(0))

        btnAddImage.setOnClickListener {

        }

        btnSave.setOnClickListener {
            saveInfo()
        }
    }

    //Saves restaurant info to database
    fun saveInfo() {
        val name = findViewById<EditText>(R.id.textInputName).text.toString()

        val rest = Restaurant(
            name,
            "",
            findViewById<EditText>(R.id.textInputAddress).text.toString(),
            findViewById<EditText>(R.id.textInputPostalCode).text.toString(),
            findViewById<EditText>(R.id.textInputCity).text.toString(),
            findViewById<EditText>(R.id.textInputPhone).text.toString(),
            findViewById<EditText>(R.id.textInputEmail).text.toString(),
            getType(),
            try { findViewById<EditText>(R.id.textInputDeliveryPrice).text.toString().toInt() } catch (e: Exception) {0} ,
            findViewById<CheckBox>(R.id.cb_takeaway).isChecked,
            findViewById<CheckBox>(R.id.cb_homeDelivery).isChecked,
            findViewById<CheckBox>(R.id.cb_atRestaurant).isChecked,
            findViewById<CheckBox>(R.id.cb_tableBooking).isChecked,
            findViewById<EditText>(R.id.textInputDescription).text.toString(),
            0.0,
            "",
            DataManagerRestaurants.restaurants.count().toString(),
            openingHours
        )
        /*
        db.collection("restaurantTibTest").document(name)
            .set(rest, SetOptions.merge())*/
    }

    fun loadRestaurant(restaurant: Restaurant) {
        findViewById<EditText>(R.id.textInputName).setText(restaurant.name)
        findViewById<EditText>(R.id.textInputAddress).setText(restaurant.address)
        findViewById<EditText>(R.id.textInputPostalCode).setText(restaurant.postalCode)
        findViewById<EditText>(R.id.textInputCity).setText(restaurant.city)
        findViewById<EditText>(R.id.textInputPhone).setText(restaurant.phoneNumber)
        findViewById<EditText>(R.id.textInputEmail).setText(restaurant.eMail)
        setType(restaurant.type)
        findViewById<EditText>(R.id.textInputDeliveryPrice).setText(restaurant.deliveryFee.toString())
        findViewById<CheckBox>(R.id.cb_takeaway).isChecked = restaurant.deliveryTypePickup
        findViewById<CheckBox>(R.id.cb_homeDelivery).isChecked = restaurant.deliveryTypeHome
        findViewById<CheckBox>(R.id.cb_atRestaurant).isChecked = restaurant.deliveryTypeAtRestaurant
        findViewById<CheckBox>(R.id.cb_tableBooking).isChecked = restaurant.tableBooking
        loadOpeningHours(restaurant.openingHours)
        findViewById<EditText>(R.id.textInputDescription).setText(restaurant.description)
    }

    fun setOpeningHours(view: View) {

        if(view is EditText) {
            val cal = Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                view.setText(SimpleDateFormat("HH:mm").format(cal.time))
                openingHours.put(view.tag.toString(), cal.time)
            }

            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    fun loadOpeningHours(oHours: HashMap<String, Date>) {
        val mView = findViewById<ConstraintLayout>(R.id.mainView)
        var cal = Calendar.getInstance()
        openingHours = oHours

        mView.forEach { et ->
            if(et is EditText && oHours.containsKey(et.tag?.toString())) {
                val date = oHours.get(et.tag.toString()) as com.google.firebase.Timestamp
                cal.time = date.toDate()
                et.setText(SimpleDateFormat("HH:mm").format(cal?.time))
            }
        }
    }

    //not optimized version
    fun setType(types: String) {
        var listOfTypes = types.split(",")

        val mView = findViewById<ConstraintLayout>(R.id.mainView)

        mView.forEach { cb ->
            if(cb is CheckBox && cb.tag?.toString() == "type" && listOfTypes.contains(cb.text.toString())) {
                cb.isChecked = true
            }
        }
    }

    fun getType() : String {
        var toReturn = ""
        val mView = findViewById<ConstraintLayout>(R.id.mainView)

        mView.forEach { cb ->
            if(cb is CheckBox && cb.tag?.toString() == "type" && cb.isChecked) {
                toReturn += cb.text.toString() + ","
            }
        }

        return toReturn.substring(0, toReturn.length - 1)
    }

}