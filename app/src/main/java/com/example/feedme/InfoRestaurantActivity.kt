package com.example.feedme

import android.app.TimePickerDialog
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class InfoRestaurantActivity : AppCompatActivity() {

    val db = Firebase.firestore
    val registerNew = false
    private var openingHours = hashMapOf<String, Calendar>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_restaurant)

        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnAddImage = findViewById<Button>(R.id.btn_add_image)
        //val loadRestaurant = intent.getStringExtra("restaurantToLoad")
        loadRestaurant("asdf")

        btnAddImage.setOnClickListener {

        }

        btnSave.setOnClickListener {
            saveInfo()
        }
    }

    fun loadRestaurant(name: String) {
        //val restaurant = DataManagerRestaurants.getByDocumentId(name)
        val docRef = db.collection("restaurantTibTest").document(name)
        var restaurant : Restaurant?  = Restaurant()
        docRef.get().addOnSuccessListener { documentSnapshot ->
            restaurant = documentSnapshot.toObject(Restaurant::class.java)
        }

        findViewById<EditText>(R.id.textInputName).setText(restaurant?.name)
        findViewById<EditText>(R.id.textInputAddress).setText(restaurant?.address)
        findViewById<EditText>(R.id.textInputPostalCode).setText(restaurant?.postalCode)
        findViewById<EditText>(R.id.textInputCity).setText(restaurant?.city)
        findViewById<EditText>(R.id.textInputPhone).setText(restaurant?.phoneNumber)
        findViewById<EditText>(R.id.textInputEmail).setText(restaurant?.eMail)
        setType(restaurant?.type ?: "")
        findViewById<EditText>(R.id.textInputDeliveryPrice).setText(restaurant?.deliveryFee ?: 0)
        findViewById<CheckBox>(R.id.cb_takeaway).isChecked = restaurant?.deliveryTypePickup ?: false
        findViewById<CheckBox>(R.id.cb_homeDelivery).isChecked = restaurant?.deliveryTypeHome ?: false
        findViewById<CheckBox>(R.id.cb_atRestaurant).isChecked = restaurant?.deliveryTypeAtRestaurant ?: false
        findViewById<CheckBox>(R.id.cb_tableBooking).isChecked = restaurant?.tableBooking ?: false
        openingHours = restaurant?.openingHours ?: openingHours
        findViewById<EditText>(R.id.textInputDescription).setText("")
    }

    fun setTime(view: View) {

        if(view is EditText) {
            val cal = Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                view.setText(SimpleDateFormat("HH:mm").format(cal.time))
                openingHours.put(view.tag.toString(), cal)
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
            null,
            openingHours,
            findViewById<EditText>(R.id.textInputDescription).text.toString()
        )

        db.collection("restaurantTibTest").document(name)
            .set(rest)
    }

    //not optimized version
    fun setType(types: String) {
        var listOfTypes = types.split(" ")

        val mView = findViewById<ConstraintLayout>(R.id.mainView)

        mView.forEach { cb ->
            if(cb is CheckBox && cb.tag?.toString() == "type" && listOfTypes.contains(cb.text.toString())) {
                cb.isChecked = true
            }
        }

    }

    fun loadOpeningHours() {
        val mView = findViewById<ConstraintLayout>(R.id.mainView)

        mView.forEach { et ->
            if(et is EditText && openingHours.containsKey(et.tag?.toString())) {
                val cal = openingHours.get(et.tag.toString())
                et.setText(SimpleDateFormat("HH:mm").format(cal?.time))
            }
        }
    }

    fun getType() : String {
        var toReturn = ""
        val mView = findViewById<ConstraintLayout>(R.id.mainView)

        mView.forEach { cb ->
            if(cb is CheckBox && cb.tag?.toString() == "type" && cb.isChecked) {
                toReturn += " " + cb.text.toString()
            }
        }

        return toReturn.trim()
    }

}