package com.example.feedme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.feedme.data.Restaurant

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRestaurantMockData()
    }

    private fun setupRestaurantMockData() {
        val restaurant1 = Restaurant(
            "Karlbergs Krog","5454-5454","Västanvindsgatan 1","44454",
            "Stenungsund","030323654","info@karlbergs.se","Husmanskost",
            50,true,true,true,
            false)
        val restaurant2 = Restaurant(
            "Restaurant 2012","5580-5465","Storgatan 12","56234",
            "Göteborg","0315648958","info@restaurant2012.se","Italienskt",
            45,true,false,true,
            true)
        val restaurant3 = Restaurant(
            "Jamie Oliver's gardens","5555-5454","Fancy Pancy street 15","12345",
            "Posh city","254685478","info@jamieoliver.com","Ala carté",
            150,true,true,true,
            true)
        val restaurant4 = Restaurant(
            "King Charles III","6555-5454","Buckingham palace 1","56458",
            "London","45213658","info@buckingham.co.uk","Ala carté",
            100,true,true,true,
            false)
    }
}