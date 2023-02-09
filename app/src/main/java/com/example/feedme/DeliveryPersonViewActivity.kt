package com.example.feedme

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.feedme.data.Restaurant
import com.google.android.gms.location.*

//Todo byta dishes till restaurants när all data är ner

class DeliveryPersonViewActivity : AppCompatActivity() {

    lateinit var deliveryRestaurantRecyclerView: RecyclerView
    lateinit var locationProvider : FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    private val REQUEST_LOCATION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_person_view)

        deliveryRestaurantRecyclerView = findViewById(R.id.RV_DeliveryViewRest)

        deliveryRestaurantRecyclerView.layoutManager = LinearLayoutManager(this)
        deliveryRestaurantRecyclerView.adapter =
            CollectOrderRecyclerAdapter(this,
                DataManagerDishes.dishes
            )

        locationProvider = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(2000).build()
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations){
                    Log.d("HHH","lat: ${location.latitude}")
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

    override fun onResume() {
        super.onResume()

       deliveryRestaurantRecyclerView.adapter?.notifyDataSetChanged()


    }

}