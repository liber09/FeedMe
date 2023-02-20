package com.example.feedme

import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import com.example.feedme.data.Restaurant
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class InfoRestaurantActivity : AppCompatActivity() {

    val db = Firebase.firestore
    var restImageUri : Uri = Uri.parse("/storage/emulated/0/Pictures/IMG_20230218_145720.jpg")//null
    val registerNew = false
    private var openingHours = hashMapOf<String, Date>(
        "monday_start" to Date(),
        "monday_end" to Date(),
        "tuesday_start" to Date(),
        "tuesday_end" to Date(),
        "wednesday_start" to Date(),
        "wednesday_end" to Date(),
        "tuesday_start" to Date(),
        "tuesday_end" to Date(),
        "friday_start" to Date(),
        "friday_end" to Date(),
        "saturday_start" to Date(),
        "saturday_end" to Date(),
        "sunday_start" to Date(),
        "sunday_end" to Date())

    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_restaurant)

        Log.v("!!!", openingHours.toString())

        val imgFile = File(restImageUri.toString())
        val imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
        findViewById<ImageView>(R.id.imageViewRestaurant).setImageBitmap(imgBitmap)

        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnAddImage = findViewById<Button>(R.id.btn_add_image)
        if(intent.hasExtra("RESTAURANT_KEY")) {
            val rest = DataManagerRestaurants.getByDocumentId(intent.getStringExtra("RESTAURANT_KEY") ?: "1")
            loadRestaurant(rest ?: Restaurant())
        }
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
            getContent.launch("image/*")
        }

        //SaveButton clickListner
        btnSave.setOnClickListener {
            saveInfo()
        }
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageUri ->
            val imageView = findViewById<ImageView>(R.id.imageViewRestaurant)
            imageView.setImageURI(imageUri)
            restImageUri = imageUri
        }
    }

    //Saves restaurant info to database
    fun saveInfo() {
        var documentRef = ""
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
            findViewById<EditText>(R.id.textInputDescription).text.toString(),
            0.0,
            "",
            "",
            openingHours
        )

        db.collection("restaurants")
            .add(rest)
            .addOnSuccessListener { documentReference ->
                Log.d("ADD RESTAURANT", "DocumentSnapshot written with ID: ${documentReference.id}")
                 documentRef = documentReference.id
            }
            .addOnFailureListener { e ->
                Log.w("ADD RESTAURANT", "Error adding document", e)
            }
        DataManagerRestaurants.update()

        //On successful save redirect to restaurant details
        val intent= Intent(this,RestaurantDetailsActivity::class.java)
        //Send extra information over to the detailsView with restaurant number
        intent.putExtra("id",documentRef.toString())
        startActivity(intent)
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

    //Checks all the type checkboxes and adds the values from the ones checked to a string and returns it
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