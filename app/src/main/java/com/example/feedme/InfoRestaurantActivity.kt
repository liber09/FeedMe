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
import com.bumptech.glide.Glide
import com.example.feedme.data.Restaurant
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
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
    lateinit var auth: FirebaseAuth
    private var imgPath = ""
    private var docIdent = ""
    private var docIt = ""
    private var docRating = 0.0
    private var openingHours = hashMapOf<String, Date>(
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_restaurant)
        auth = Firebase.auth
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var emailFromSignUp = intent.getStringExtra("mail").toString()
        var eMail = findViewById<EditText>(R.id.textInputEmail)
        eMail.setText(emailFromSignUp)


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnAddImage = findViewById<Button>(R.id.btn_add_image)
        if(intent.hasExtra("RESTAURANT_KEY")) {
            val rest = DataManagerRestaurants.getByDocumentId(intent.getStringExtra("RESTAURANT_KEY") ?: "")
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
        val documentIternal = if(docIt.isEmpty()) "${user.uid}" else docIt
        val documentId = if(docIdent.isEmpty()) "${user.uid}+1" else docIdent
        // TODO gör det till i med increment när man väl kan lägga upp fler restauaranger


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
            findViewById<EditText>(R.id.textInputDescription).text.toString(),
            docRating,
            if(imageUri != null) "/restaurants/$fileName" else imgPath,
            documentIternal,
            documentId,
            openingHours
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
        imgPath = restaurant.imagePath

        findViewById<EditText>(R.id.textInputName).setText(restaurant.name)
        findViewById<EditText>(R.id.textInputAddress).setText(restaurant.address)
        findViewById<EditText>(R.id.textInputPostalCode).setText(restaurant.postalCode)
        findViewById<EditText>(R.id.textInputCity).setText(restaurant.city)
        findViewById<EditText>(R.id.textInputPhone).setText(restaurant.phoneNumber)
        findViewById<EditText>(R.id.textInputEmail).setText(restaurant.email)
        findViewById<EditText>(R.id.textInputOrgNr).setText(restaurant.orgNr)
        setType(restaurant.type)
        findViewById<EditText>(R.id.textInputDeliveryPrice).setText(restaurant.deliveryFee.toString())
        findViewById<CheckBox>(R.id.cb_takeaway).isChecked = restaurant.deliveryTypePickup
        findViewById<CheckBox>(R.id.cb_homeDelivery).isChecked = restaurant.deliveryTypeHome
        findViewById<CheckBox>(R.id.cb_atRestaurant).isChecked = restaurant.deliveryTypeAtRestaurant
        findViewById<CheckBox>(R.id.cb_tableBooking).isChecked = restaurant.tableBooking
        loadOpeningHours(restaurant.openingHours)
        findViewById<EditText>(R.id.textInputDescription).setText(restaurant.description)
        val imgRestaurant = findViewById<ImageView>(R.id.imageViewRestaurant)
        //from customerMyPage
        if(restaurant.imagePath.isNotEmpty()) {
            val imageref = Firebase.storage.reference.child(restaurant.imagePath)
            imageref.downloadUrl.addOnSuccessListener { Uri ->
                val imageURL = Uri.toString() // get the URL for the image
                //Use third party product glide to load the image into the imageview
                Glide.with(this)
                    .load(imageURL)
                    .into(imgRestaurant)
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
                val date = oHours.get(et.tag.toString())
                cal.time = date
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

    fun checkOrgNr(orgNr : String) : Boolean {
        var passed = true

        if(orgNr.length < 10 || orgNr.length > 11 || orgNr.get(2).digitToInt() < 2) passed = false

        //orgNr = orgNr.replace("-", "")

        return passed
    }
}