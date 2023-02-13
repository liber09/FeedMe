package com.example.feedme
import android.graphics.Color

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil

class DeliveryMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_maps)

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

        // Define the origin and destination points
        val origin = LatLng(56.332639461378015, 13.671913141231613)
        val destination = LatLng(56.155231527347276, 13.756392685407457)

        // Add a marker in the location and move the camera
        mMap.addMarker(MarkerOptions().position(origin).title("Origin"))
        mMap.addMarker(MarkerOptions().position(destination).title("Destination"))

        // Get the driving directions between the two points
        DirectionsApi.getDrivingDirections(origin, destination) { latLngs ->
            // Draw the polyline on the map
            val polylineOptions = PolylineOptions()
                .color(Color.RED)
                .width(5f)
                .addAll(latLngs)
            mMap.addPolyline(polylineOptions)

            // Move the camera to fit the bounds of the polyline
            val builder = LatLngBounds.Builder()
            latLngs.forEach { builder.include(it) }
            val bounds = builder.build()
            val padding = 100
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            mMap.moveCamera(cameraUpdate)
        }


    /*private fun getDirections(origin: LatLng, destination: LatLng) {
        val directions = DirectionsApi.newRequest(geoApiContext)
            .mode(TravelMode.DRIVING)
            .origin(Point.fromLngLat(origin.longitude, origin.latitude))
            .destination(Point.fromLngLat(destination.longitude, destination.latitude))
            .await()

        if (directions.routes.isNotEmpty()) {
            val route = directions.routes[0].overviewPolyline

            val decodedPath = PolyUtil.decode(route.encodedPath)
            val polyline = PolylineOptions()
                .addAll(decodedPath)
                .width(10f)
                .color(Color.RED)

            mMap.addPolyline(polyline)

            val bounds = LatLngBounds.Builder()
                .include(origin)
                .include(destination)
                .build()

            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 100)
            mMap.moveCamera(cameraUpdate)
        }*/
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