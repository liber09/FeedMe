package com.example.feedme

import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import com.example.feedme.data.Restaurant
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

var fileName: String = ""
class InfoRestaurantActivity : AppCompatActivity() {

    val db = Firebase.firestore
    private val pickImage = 100
    private var imageUri: Uri? = null
    val registerNew = false
    lateinit var auth: FirebaseAuth
    //private var openingHours = hashMapOf<String, Date>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_restaurant)
        auth = Firebase.auth

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
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        //SaveButton clickListner
        btnSave.setOnClickListener {
            saveInfo()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            val addImageView = findViewById<ImageView>(R.id.imageViewRestaurant)
            addImageView.setImageURI(imageUri)
        }
    }
    var documentRef = ""
    //Saves restaurant info to database
        fun saveInfo() {
            val user = auth.currentUser


        if (user == null){
            return

           }
        val documentIternal = "${user.uid}"
        val documentId = "${user.uid}+1"


        imageUri?.let { uploadImageToFirebase(it) }
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
                "",
                0.0,
                "/restaurants/$fileName",
                documentIternal,
                //openingHours
            documentId

            )

        db.collection("restaurants")
            .document(documentId)
            .set(rest)
            .addOnSuccessListener { documentReference ->
                Log.d("ADD RESTAURANT", "DocumentSnapshot written with ID: ${rest.documentId}")
                 documentRef = documentId
            }
            .addOnFailureListener { e ->
                Log.w("ADD RESTAURANT", "Error adding document", e)
            }
        DataManagerRestaurants.update()

        //On successful save redirect to restaurant details
        val intent= Intent(this,RestaurantDetailsActivity::class.java)
        //Send extra information over to the detailsView with restaurant number
        intent.putExtra("userid", user.uid)
        intent.putExtra("id",documentId)
       intent.putExtra("restid",documentId)

        Log.d("KKK",documentId)
        startActivity(intent)
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        if (fileUri != null) {
            fileName = UUID.randomUUID().toString() +".jpg" //Set filename

            val refStorage = Firebase.storage.reference.child("restaurants/$fileName")

            //Upload the file
            refStorage.putFile(fileUri)
                .addOnSuccessListener(
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()
                        }
                    })

                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }
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
        //
        // /*loadOpeningHours(restaurant.openingHours)
        findViewById<EditText>(R.id.textInputDescription).setText(restaurant.description)

    }
    /*
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
    */
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