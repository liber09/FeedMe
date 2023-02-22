package com.example.feedme

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

lateinit var RVCustomerOrderConfirmation : RecyclerView
class CustomerOrderConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_order_confirmation)
        RVCustomerOrderConfirmation = findViewById<RecyclerView>(R.id.RVCustomerOrderConfirmation)
        RVCustomerOrderConfirmation.layoutManager= LinearLayoutManager(this)
        val adapter = CustomerOrderConfirmationRVAdapter(this,DataManagerShoppingCart.shoppingCartItems)
        RVCustomerOrderConfirmation.adapter = adapter
        val ar1 = intent.extras!!.getStringArrayList("list")
        val customerData = ar1?.get(0)?.split(" ")?.toMutableList()
        customerData?.removeAll(listOf("",null))

        val sb = StringBuilder()
        sb.append(customerData?.get(0).toString()).append(" ").append(customerData?.get(1).toString())
        val nameView = findViewById<TextView>(R.id.TVOrderConfirmationCustomerName)
        nameView.text = sb.toString()
        val addressView = findViewById<TextView>(R.id.TVOrderConfirmationCustomerAddress)
        sb.clear()
        sb.append(customerData?.get(2).toString()).append(" ").append(customerData?.get(3).toString())
        if (customerData != null) {
            addressView.text = sb.toString()
        }
        val postalCodeView = findViewById<TextView>(R.id.TVOrderConfirmationCustomerPostalCode)
        if (customerData != null) {
            postalCodeView.text = customerData.get(4).toString()
        }
        val cityView = findViewById<TextView>(R.id.TVOrderConfirmationCustomerCity)
        if (customerData != null) {
            cityView.text = customerData.get(5).toString()
        }
        val orderDateTime = findViewById<TextView>(R.id.TVOrderConfirmationCustomerOrderDate)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val current = LocalDateTime.now().format(formatter)
        orderDateTime.text = current.toString()
    }
}