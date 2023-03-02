package com.example.feedme

import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import com.bumptech.glide.Glide
import com.example.feedme.data.Restaurant
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

var fileName: String = ""

const val RESTAURANT_POSTION_KEY = "RESTAURANT_POSITION"
const val RESTAURANT_POSITION_NOT_SET = -1
class InfoRestaurantActivity : AppCompatActivity() {

    val db = Firebase.firestore
    private val pickImage = 100
    private var imageUri: Uri? = null
    lateinit var auth: FirebaseAuth
    private var imgPath = ""
    private var docIdent = ""
    private var docIt = ""
    private var docRating = 0.0
    private var openingHours = hashMapOf(
        "monday_start" to Date(),
        "monday_end" to Date(),
        "tuesday_start" to Date(),
        "tuesday_end" to Date(),
        "wednesday_start" to Date(),
        "wednesday_end" to Date(),
        "thursday_start" to Date(),
        "thursday_end" to Date(),
        "friday_start" to Date(),
        "friday_end" to Date(),
        "saturday_start" to Date(),
        "saturday_end" to Date(),
        "sunday_start" to Date(),
        "sunday_end" to Date())

    lateinit var textInputName : EditText
    lateinit var textInputAddress : EditText
    lateinit var textInputPostalCode : EditText
    lateinit var textInputCity : EditText
    lateinit var textInputPhone : EditText
    lateinit var textInputEmail : EditText
    lateinit var textInputOrgNr : EditText
    lateinit var textInputDeliveryPrice : EditText
    lateinit var cb_takeAway : CheckBox
    lateinit var cb_homeDelivery : CheckBox
    lateinit var cb_atRestaurant : CheckBox
    lateinit var cb_tableBooking : CheckBox
    lateinit var textInputDescription : EditText
    lateinit var imageViewRestaurant : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_restaurant)
        auth = Firebase.auth
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var emailFromSignUp = intent.getStringExtra("mail").toString()
        var eMail = findViewById<EditText>(R.id.textInputEmail)
        eMail.setText(emailFromSignUp)
        val restPosition = intent.getIntExtra(RESTAURANT_POSTION_KEY, RESTAURANT_POSITION_NOT_SET)

        textInputName = findViewById<EditText>(R.id.textInputName)
        textInputAddress = findViewById<EditText>(R.id.textInputAddress)
        textInputPostalCode = findViewById<EditText>(R.id.textInputPostalCode)
        textInputCity = findViewById<EditText>(R.id.textInputCity)
        textInputPhone = findViewById<EditText>(R.id.textInputPhone)
        textInputEmail = findViewById<EditText>(R.id.textInputEmail)
        textInputOrgNr = findViewById<EditText>(R.id.textInputOrgNr)
        textInputDeliveryPrice = findViewById<EditText>(R.id.textInputDeliveryPrice)
        cb_takeAway = findViewById<CheckBox>(R.id.cb_takeaway)
        cb_homeDelivery = findViewById<CheckBox>(R.id.cb_homeDelivery)
        cb_atRestaurant = findViewById<CheckBox>(R.id.cb_atRestaurant)
        cb_tableBooking = findViewById<CheckBox>(R.id.cb_tableBooking)
        textInputDescription = findViewById<EditText>(R.id.textInputDescription)
        imageViewRestaurant = findViewById<ImageView>(R.id.imageViewRestaurant)


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnAddImage = findViewById<Button>(R.id.btn_add_image)
        if(intent.hasExtra("restId")) {
            val rest = DataManagerRestaurants.getByDocumentId(intent.getStringExtra("restId") ?: "")
            if(rest != null) loadRestaurant(rest)
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
            val addImageView = imageViewRestaurant
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
        val documentIternal = if(docIt.isEmpty()) "${user.uid}" else docIt
        val documentId = if(docIdent.isEmpty()) "${user.uid}+1" else docIdent
        // TODO gör det till i med increment när man väl kan lägga upp fler restauaranger
        if (imageUri != null) {
            imageUri?.let { uploadImageToFirebase(it) }
            imgPath = "/restaurants/$fileName"
        }
        var deliveryPrice = 0
        if (textInputDeliveryPrice.text.toString().isNotEmpty()) {
            try {
                deliveryPrice =
                    textInputDeliveryPrice.text.toString().toInt()
            } catch (e: Exception) {

                Toast.makeText(this, "Bara siffror godtas i Utkärningsavgiften", Toast.LENGTH_SHORT)
                    .show()
                return
            }
        }


        val rest = Restaurant(
            textInputName.text.toString(),
            textInputOrgNr.text.toString(),
            textInputAddress.text.toString(),
            textInputPostalCode.text.toString(),
            textInputCity.text.toString(),
            textInputPhone.text.toString(),
            textInputEmail.text.toString(),
            getType(),
            deliveryPrice,
            cb_takeAway.isChecked,
            cb_homeDelivery.isChecked,
            cb_atRestaurant.isChecked,
            cb_tableBooking.isChecked,
            textInputDescription.text.toString(),
            docRating,
            imgPath,
            documentIternal,
            documentId,
            openingHours
        )
        if (textInputName.text.toString().isEmpty()) {
            Toast.makeText(this, "Restaurant name cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

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

        //update

        val restaurantssRef = db.collection("restaurants")
        restaurantssRef.addSnapshotListener { snapshot, e ->
            if (snapshot != null) {
                DataManagerRestaurants.restaurants.clear()
                for (document in snapshot.documents) {
                    if (document != null) {
                        document.toObject<Restaurant>()
                            ?.let { DataManagerRestaurants.restaurants.add(it) }
                    }
                }
            }
        }


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
        imgPath = restaurant.imagePath

        textInputName.setText(restaurant.name)
        textInputAddress.setText(restaurant.address)
        textInputPostalCode.setText(restaurant.postalCode)
        textInputCity.setText(restaurant.city)
        textInputPhone.setText(restaurant.phoneNumber)
        textInputEmail.setText(restaurant.email)
        textInputOrgNr.setText(restaurant.orgNr)
        setType(restaurant.type)
        textInputDeliveryPrice.setText(restaurant.deliveryFee.toString())
        cb_takeAway.isChecked = restaurant.deliveryTypePickup
        cb_homeDelivery.isChecked = restaurant.deliveryTypeHome
        cb_atRestaurant.isChecked = restaurant.deliveryTypeAtRestaurant
        cb_tableBooking.isChecked = restaurant.tableBooking
        loadOpeningHours(restaurant.openingHours)
        textInputDescription.setText(restaurant.description)

        //from customerMyPage
        if(restaurant.imagePath.isNotEmpty()) {
            val imageref = Firebase.storage.reference.child(restaurant.imagePath)
            imageref.downloadUrl.addOnSuccessListener { Uri ->
                val imageURL = Uri.toString() // get the URL for the image
                //Use third party product glide to load the image into the imageview
                Glide.with(this)
                    .load(imageURL)
                    .into(imageViewRestaurant)
            }
        }
        docIdent = restaurant.documentId!!
        docRating = restaurant.rating!!
        docIt = if(!restaurant.documentInternal.isNullOrEmpty()) restaurant.documentInternal!! else ""
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
                cal.time = oHours.get(et.tag.toString())
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

        return if(toReturn.length > 0) toReturn.substring(0, toReturn.length - 1) else ""
    }

    fun checkOrgNr(orgNr : String) : Boolean {
        var passed = true

        if(orgNr.length < 10 || orgNr.length > 11 || orgNr.get(2).digitToInt() < 2) passed = false

        //orgNr = orgNr.replace("-", "")

        return passed
    }
}