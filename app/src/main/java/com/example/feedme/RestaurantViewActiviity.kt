package com.example.feedme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gtappdevelopers.kotlingfgproject.SliderAdapter
import com.smarteist.autoimageslider.SliderView

class RestaurantViewActiviity : AppCompatActivity(), RestaurantViewRVAdapter.OnClickListener {

    lateinit var restaurantRecyclerView: RecyclerView
    // on below line we are creating a variable
    // for our array list for storing our images.
    lateinit var imageUrl: ArrayList<String>

    // on below line we are creating
    // a variable for our slider view.
    lateinit var sliderView: SliderView

    // on below line we are creating
    // a variable for our slider adapter.
    lateinit var sliderAdapter: SliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_view_activiity)

        // on below line we are initializing our slier view.
        sliderView = findViewById(R.id.slider)

        // on below line we are initializing
        // our image url array list.
        imageUrl = ArrayList()

        // on below line we are adding data to our image url array list.
        imageUrl =
            (imageUrl + "https://images.squarespace-cdn.com/content/v1/5c03f0aa4611a0c1b2887881/1593069740328-TL8FXMG1DHAQVFI2OXQN/Varsommar%2Bkampanj%2B2020%2Brosa%2Bv24%2Bwebbplug2.jpg?format=750w") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://www.allakartor.se/venue_images_475/480964_243168395879090.jpg") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://lepainfrancais.se/wp-content/uploads/2020/03/erbjudande-a-la-carte_utkast3-300x300.jpg") as ArrayList<String>

        // on below line we are initializing our
        // slider adapter and adding our list to it.
        sliderAdapter = SliderAdapter( imageUrl)

        // on below line we are setting auto cycle direction
        // for our slider view from left to right.
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        // on below line we are setting adapter for our slider.
        sliderView.setSliderAdapter(sliderAdapter)

        // on below line we are setting scroll time
        // in seconds for our slider view.
        sliderView.scrollTimeInSec = 3

        // on below line we are setting auto cycle
        // to true to auto slide our items.
        sliderView.isAutoCycle = true

        // on below line we are calling start
        // auto cycle to start our cycle.
        sliderView.startAutoCycle()


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        restaurantRecyclerView = findViewById(R.id.rv_Restaurant)
        restaurantRecyclerView.layoutManager = LinearLayoutManager(this)
        restaurantRecyclerView.adapter = RestaurantViewRVAdapter(this,DataManagerRestaurants.restaurants, this)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val backButton = findViewById<ImageView>(R.id.foodViewBackButton)
        backButton.setOnClickListener{
            finish()
        }
        val logo = findViewById<ImageView>(R.id.LogoText)
        logo.setOnClickListener{
            val intent= Intent(this,CheatActivity::class.java)
            startActivity(intent)
        }

        val profilebutton = findViewById<Button>(R.id.profileButton)

        profilebutton.setOnClickListener {
            val intent= Intent(this,CustomerMyPages::class.java)
            startActivity(intent)

        }






    }
    override fun onResume() {
        super.onResume()



        restaurantRecyclerView.adapter?.notifyDataSetChanged()




    }

        override fun OnClick(position: Int) {
        val intent = Intent(this, RestaurantDetailsActivity::class.java)
            val restId =DataManagerRestaurants.restaurants[position].documentId.toString()

            intent.putExtra("restid", restId)
            Log.d("TTT","$restId")
        //intent.putExtra("id", DataManagerRestaurants.restaurants[position].documentId.toString())
        startActivity(intent)


    }
}