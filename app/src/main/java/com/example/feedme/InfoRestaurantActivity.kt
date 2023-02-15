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
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class InfoRestaurantActivity : AppCompatActivity() {

    val db = Firebase.firestore
    val registerNew = false
    private var openingHours = hashMapOf<String, Date>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_restaurant)

        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnAddImage = findViewById<Button>(R.id.btn_add_image)
        //val loadRestaurant = intent.getStringExtra("restaurantToLoad")
        //loadRestaurant("asdf")

        val restaurantRef = db.collection("restaurantTibTest")
        restaurantRef.addSnapshotListener{ snapshot, e ->
            if (snapshot != null) {
                DataManagerRestaurants.restaurants.clear()

                for (document in snapshot.documents)
                {
                    val item = Restaurant(
                        document.get("name").toString(),
                        document.get("orgNr").toString(),
                        document.get("address").toString(),
                        document.get("postalCode").toString(),
                        document.get("city").toString(),
                        document.get("phoneNumber").toString(),
                        document.get("email").toString(),
                        document.get("type").toString(),
                        document.get("deliveryFee").toString().toInt(),
                        document.get("deliveryTypePickup").toString().toBoolean(),
                        document.get("deliveryTypeHome").toString().toBoolean(),
                        document.get("deliveryTypeAtRestaurant").toString().toBoolean(),
                        document.get("tableBooking").toString().toBoolean(),
                        document.get("description").toString(),
                        document.get("rating").toString().toDouble(),
                        document.get("imagePath").toString(),
                        document.get("documentId").toString(),
                        document.get("openingHours") as HashMap<String, Date>
                    )

                    if (item != null) {
                        //DataManagerRestaurants.restaurants.add(item)
                        loadRestaurant(item)
                    }
                }
            }
        }

        btnAddImage.setOnClickListener {

        }

        btnSave.setOnClickListener {
            saveInfo()
        }
    }

    fun loadRestaurant(restaurant: Restaurant) {
        Log.v("!!!", restaurant.name)
        findViewById<EditText>(R.id.textInputName).setText(restaurant.name)
        findViewById<EditText>(R.id.textInputAddress).setText(restaurant.address)
        findViewById<EditText>(R.id.textInputPostalCode).setText(restaurant.postalCode)
        findViewById<EditText>(R.id.textInputCity).setText(restaurant.city)
        findViewById<EditText>(R.id.textInputPhone).setText(restaurant.phoneNumber)
        findViewById<EditText>(R.id.textInputEmail).setText(restaurant.eMail)
        setType(restaurant.type ?: "")
        findViewById<EditText>(R.id.textInputDeliveryPrice).setText(restaurant.deliveryFee.toString() ?: "0")
        findViewById<CheckBox>(R.id.cb_takeaway).isChecked = restaurant.deliveryTypePickup ?: false
        findViewById<CheckBox>(R.id.cb_homeDelivery).isChecked = restaurant.deliveryTypeHome ?: false
        findViewById<CheckBox>(R.id.cb_atRestaurant).isChecked = restaurant.deliveryTypeAtRestaurant ?: false
        findViewById<CheckBox>(R.id.cb_tableBooking).isChecked = restaurant.tableBooking ?: false
        loadOpeningHours(restaurant.openingHours)
        findViewById<EditText>(R.id.textInputDescription).setText(restaurant.description ?: "")
    }

    fun setTime(view: View) {

        if(view is EditText) {
            val cal = Calendar.getInstance()
            cal.time = Date()
            //val date = Date()

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
            null,
            openingHours
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

    fun loadOpeningHours(oHours: HashMap<String, Date>) {
        val mView = findViewById<ConstraintLayout>(R.id.mainView)
        var cal = Calendar.getInstance()

        mView.forEach { et ->
            if(et is EditText && oHours.containsKey(et.tag?.toString())) {
                val date = oHours.get(et.tag.toString()) as com.google.firebase.Timestamp
                cal.time = date.toDate()
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