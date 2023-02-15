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
import com.example.feedme.data.Restaurant
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class InfoRestaurantActivity : AppCompatActivity() {

    val db = Firebase.firestore
    val registerNew = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_restaurant)

        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnAddImage = findViewById<Button>(R.id.btn_add_image)
        val monSta = findViewById<EditText>(R.id.textInputMondayStart)
        val editName = findViewById<EditText>(R.id.textInputName)

        monSta.setOnClickListener {
            Log.v("!!!","clicked")

            val cal = Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                monSta.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }

            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        btnAddImage.setOnClickListener {

        }

        //SaveButton clickListner
        btnSave.setOnClickListener {
            saveInfo()
        }
    }
    //Saves restaurant info to database
    private fun saveInfo() {

        val rest = Restaurant(
            findViewById<EditText>(R.id.textInputName).text.toString(),
            findViewById<EditText>(R.id.textInputOrgNr).text.toString(),
            findViewById<EditText>(R.id.textInputAddress).text.toString(),
            findViewById<EditText>(R.id.textInputPostalCode).text.toString(),
            findViewById<EditText>(R.id.textInputCity).text.toString(),
            findViewById<EditText>(R.id.textInputPhone).text.toString(),
            findViewById<EditText>(R.id.textInputEmail).text.toString(),
            getType(),
            findViewById<EditText>(R.id.textInputDeliveryPrice).text.toString().toInt(),
            findViewById<CheckBox>(R.id.cb_takeaway).isChecked,
            findViewById<CheckBox>(R.id.cb_homeDelivery).isChecked,
            findViewById<CheckBox>(R.id.cb_atRestaurant).isChecked,
            findViewById<CheckBox>(R.id.cb_tableBooking).isChecked,
        )

        db.collection("restaurants").document()
            .set(rest)
        //On successful save redirect to restaurant details
        val intent= Intent(this,RestaurantDetailsActivity::class.java)
        //Send extra information over to the detailsView with restaurant number
        val resNumber = DataManagerRestaurants.restaurants.count()
        intent.putExtra("RESTAURANT_KEY",resNumber)
        startActivity(intent)
    }

    //Checks all the type checkboxes and adds the values from the ones checked to a string and returns it
    fun getType() : String {
        var toReturn = ""
        val mView = findViewById<ConstraintLayout>(R.id.mainView)

        for(i in 0 .. mView.childCount-1) {
            val cb = mView.getChildAt(i)
            if(cb is CheckBox && cb.tag?.toString() == "type" && cb.isChecked) {
                toReturn += " " + cb.text.toString()
            }
        }

        return toReturn.trim()
    }

}