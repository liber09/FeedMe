package com.example.feedme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}