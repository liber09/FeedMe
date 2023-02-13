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


class InfoRestaurantActivity : AppCompatActivity() {

    val db = Firebase.firestore
    val registerNew = false
    val openingHours = hashMapOf<String, Calendar>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_restaurant)

        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnAddImage = findViewById<Button>(R.id.btn_add_image)

        btnAddImage.setOnClickListener {

        }

        btnSave.setOnClickListener {
            saveInfo()
        }
    }

    fun setTime(view: View) {

        if(view is EditText) {
            val cal = Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                view.setText(SimpleDateFormat("HH:mm").format(cal.time))
                openingHours.put(view.tag.toString(), cal);
                Log.v("!!!", openingHours.size.toString() + " " + view.tag.toString() + " " + view.text.toString())
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

        /*db.collection("restaurant").document(name)
            .set(rest)*/
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