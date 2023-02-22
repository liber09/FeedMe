package com.example.feedme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.R

lateinit var RVCustomerOrderConfirmation : RecyclerView
class CustomerOrderConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_order_confirmation)
        RVCustomerOrderConfirmation = findViewById<RecyclerView>(R.id.RVCustomerOrderConfirmation)
        RVCustomerOrderConfirmation.layoutManager= LinearLayoutManager(this)
        val adapter = CustomerOrderConfirmationRVAdapter(this,DataManagerShoppingCart.shoppingCartItems)
        RVCustomerOrderConfirmation.adapter = adapter
    }
}