package com.example.feedme

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.data.Dishes
import com.example.feedme.data.Order

import com.example.feedme.data.Restaurant
import com.google.android.gms.location.*
import java.text.ParsePosition

//Todo byta dishes till restaurants när all data är ner

class DeliveryPersonViewActivity : AppCompatActivity(), CollectOrderRecyclerAdapter.OnClickListener {

    lateinit var deliveryRestaurantRecyclerView: RecyclerView
    lateinit var locationProvider : FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    private val REQUEST_LOCATION = 1
    lateinit var restaurantsWithOrders : List<Restaurant>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_person_view)

        val logo = findViewById<ImageView>(R.id.iv_Logo)
        logo.setOnClickListener{

            val intent= Intent(this,CheatActivity::class.java)
            startActivity(intent)
        }

        val profileBtn = findViewById<ImageButton>(R.id.profileButton)

        profileBtn.setOnClickListener {
            val intent= Intent(this,SignInDeliveryPerson::class.java)
            startActivity(intent)

        }

        deliveryRestaurantRecyclerView = findViewById(R.id.RV_DeliveryViewRest)

        deliveryRestaurantRecyclerView.layoutManager = LinearLayoutManager(this)
        /*deliveryRestaurantRecyclerView.adapter =
            CollectOrderRecyclerAdapter(this,
                DataManagerRestaurants.restaurants

            )*/

         restaurantsWithOrders = mutableListOf<Restaurant>()

        for (restaurant in DataManagerRestaurants.restaurants){

            Log.d("tag", "$restaurant")

        val orderReference = db.collection("restaurants")
            .document(restaurant.documentId.toString())
            .collection("orders")

       orderReference.get().addOnSuccessListener { snapshot->
            if ( snapshot.isEmpty) {
                Log.d("lalaa", "tomt")
                return@addOnSuccessListener
            }
           (restaurantsWithOrders as MutableList<Restaurant>).add(restaurant)
           Log.d("restaurants", "Name of restaurant at position  ${restaurantsWithOrders.indexOf(restaurant)}: ${restaurant.name}")


                deliveryRestaurantRecyclerView.adapter =
                    CollectOrderRecyclerAdapter(
                        this,
                        restaurantsWithOrders,this

                    )

                //foodRecyclerView.adapter = adapter*/


            }
        }





        locationProvider = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(2000).build()
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations){
                    Log.d("HHH","lat: ${location.latitude}")
                    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                }
            }
        }

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION)

        }








    }
    fun startLocationUpdates(){

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){

            locationProvider.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())



        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_LOCATION){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startLocationUpdates()

            }


        }
    }

    override fun OnClick(position: Int){
      //val target = DataManagerRestaurants.restaurants[position]
        val targetaddress = restaurantsWithOrders[position]
            .address+", "+restaurantsWithOrders[position]
            .postalCode+" "+restaurantsWithOrders[position].city


                       val intent = Intent(this,DeliveryMapsActivity::class.java)
                       intent.putExtra("target","$targetaddress")
                       this.startActivity(intent)


    }

    override fun onResume() {
        super.onResume()

       deliveryRestaurantRecyclerView.adapter?.notifyDataSetChanged()
        startLocationUpdates()


    }

    // on pause behöver jag kanske inte riktig än - jag tänker jag

}