package com.example.feedme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.feedme.data.Customer
import com.google.firebase.firestore.ktx.toObject

object MyPagesCustomer{
    var customer: Customer = Customer()
}
class CustomerMyPages : AppCompatActivity() {

    lateinit var btnMyPagesEdit : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_my_pages)
        updateViewsWithInfo(MyPagesCustomer.customer)

        btnMyPagesEdit = findViewById<Button>(R.id.btnMyPagesEdit)
        btnMyPagesEdit.setOnClickListener {
            val intent = Intent(this, RegisterCustomerInfo::class.java)
            intent.putExtra("ID", MyPagesCustomer.customer.customerId)
            startActivity(intent)
        }
    }

    fun updateViewsWithInfo(customer:Customer?){
        val TVMyPagesFirstName = findViewById<TextView>(R.id.TVMyPagesFirstName)
        val TVMyPagesLastName = findViewById<TextView>(R.id.TVMyPagesLastName)
        val TVMyPagesAddress = findViewById<TextView>(R.id.TVMyPagesAddress)
        val TVMyPagesPostalCode = findViewById<TextView>(R.id.TVMyPagesPostalCode)
        val TVMyPagesCIty = findViewById<TextView>(R.id.TVMyPagesCIty)
        val TVMyPagesMobile = findViewById<TextView>(R.id.TVMyPagesMobile)
        val TVMyPagesEmail = findViewById<TextView>(R.id.TVMyPagesEmail)

        TVMyPagesFirstName.text = customer?.firstName
        TVMyPagesLastName.text = customer?.lastName
        TVMyPagesAddress.text = customer?.address
        TVMyPagesPostalCode.text = customer?.postalCode
        TVMyPagesCIty.text = customer?.city
        TVMyPagesMobile.text = customer?.phoneNumber
        TVMyPagesEmail.text = customer?.eMail
    }
}