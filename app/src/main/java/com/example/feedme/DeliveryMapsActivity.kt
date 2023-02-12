package com.example.feedme

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.feedme.data.Restaurant

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.feedme.databinding.ActivityDeliveryMapsBinding
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions

class DeliveryMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDeliveryMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeliveryMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //TODO get the address from the chosen restaurant Class
        //get the current location of the driver
        //find out hou googleMapsguidning works

        // Add a marker in the location and move the camera


    }
}

       // https://www.youtube.com/watch?v=GHIyf7oXT1w&list=PLCf5IsO6cvqGi6ddt5zt-HOirgQwSbJx6&index=1



   /*     val address = "Gamla vägen 12, 282 67 Vittsjö, SWEDEN"
        val location = getLocationFromAddress(address)
        if (location != null) {
            val latLng = LatLng(location.latitude, location.longitude)
            mMap.addMarker(MarkerOptions().position(latLng).title(address))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

            if (latLng != null) {
                val boundBuilder = LatLngBounds.Builder().include(latLng)
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundBuilder.build(), 1000, 1000, 0))
            }
        }

    }

    //TODO Get this righ


    private fun getLocationFromAddress(address: String): LatLng? {
        val geocoder = Geocoder(this)
        val addresses = geocoder.getFromLocationName(address, 1)
        if (addresses!!.isNotEmpty()) {
            val location = addresses[0]
            return LatLng(location.latitude, location.longitude)


        }
        return null
}


}

*/