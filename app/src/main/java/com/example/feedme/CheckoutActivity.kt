package com.example.feedme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val btnCompleteOrder = findViewById<Button>(R.id.btnFinishOrder)
        btnCompleteOrder.setOnClickListener{
            val intent = Intent(this, CustomerOrderConfirmationActivity::class.java)
            startActivity(intent)
        }
    }
}