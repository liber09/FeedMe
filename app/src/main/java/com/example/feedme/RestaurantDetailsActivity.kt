package com.example.feedme

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.feedme.data.Dishes
import com.example.feedme.data.Restaurant
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.util.*

private val pickImage = 100
private var imageUri: Uri? = null
object State{
    var restaurantId: String = ""
}

class RestaurantDetailsActivity : AppCompatActivity() {

    lateinit var restaurantTitel :TextView
    lateinit var restaurantdescripton :TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)
        val intent:Intent = getIntent()
        State.restaurantId = intent.getStringExtra("id").toString()
        Log.d("MMM","$State.restaurantId")

        val restID = intent.getStringExtra("id")
        Log.d("MMM","$restID")

        restaurantTitel = findViewById(R.id.tv_restTitle_details)


        restaurantdescripton = findViewById(R.id.tv_Rest_Descript_RestDetails)
        val menueButton = findViewById<Button>(R.id.btn_menu)
        val changeImageButton = findViewById<Button>(R.id.btnChangeImage)
        changeImageButton.setOnClickListener{
            changeImage()
        }
        for (restaurant in DataManagerRestaurants.restaurants){
            val restaurantImage = findViewById<ImageView>(R.id.imgRestaurant)
            if(State.restaurantId == restaurant.documentId){
                restaurantTitel.text = restaurant.name
                restaurantdescripton.text = restaurant.description

                //Get the image from firebase
                if(restaurant.imagePath.isNotEmpty()) {
                    val imageref = Firebase.storage.reference.child(restaurant.imagePath)
                    imageref.downloadUrl.addOnSuccessListener { Uri ->
                        val imageURL = Uri.toString() // get the URL for the image
                        //Use third party product glide to load the image into the imageview
                        Glide.with(this)
                            .load(imageURL)
                            .into(restaurantImage)
                    }
                }


                val docRef =db.collection("restaurants").document(State.restaurantId!!).collection("dishes")
                docRef.addSnapshotListener{ snapshot, e ->
                    if (snapshot != null) {

                        DataManagerDishes.dishes.clear()
                        for (document in snapshot.documents)

                        { val item = document.toObject<Dishes>()
                            //Get parent documentId - restaurant in this case
                            item?.restaurantDocumentId = document.reference.parent.parent?.id.toString()
                            if (item != null) {
                                DataManagerDishes.dishes.add(item)
                            }
                        }

                        printDishes()
                    }
                }




                menueButton.setOnClickListener {

                    val intent= Intent(this,FoodViewActivity::class.java)
                    intent.putExtra("id", State.restaurantId)
                    intent.putExtra("restid",restID)



                    startActivity(intent)


                }



            }


        }



    }

    private fun changeImage() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, pickImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            val addImageView = findViewById<ImageView>(R.id.imgRestaurant)
            addImageView.setImageURI(imageUri)
            imageUri?.let { uploadImageToFirebase(it) }
            SaveImagePathToDb()
        }
    }

    private fun SaveImagePathToDb(){
        for (restaurant in DataManagerRestaurants.restaurants){
            if(State.restaurantId == restaurant.documentId){
                db.collection("restaurants").document(State.restaurantId).update(mapOf("imagePath" to "restaurants/$fileName") )
            }
        }
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

    fun  printDishes(){

        for (item in DataManagerDishes.dishes)
        {
            Log.d("HHH", "${item.title}")

        }


    }



}