package com.example.feedme

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.feedme.data.Customer
import com.example.feedme.data.Dishes
import com.example.feedme.data.Order
import com.google.firebase.firestore.ktx.toObject
import org.w3c.dom.Text
object MyPagesCustomer{
    var customer: Customer = Customer()
}
class CustomerMyPages : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_my_pages)
        updateViewsWithInfo(MyPagesCustomer.customer)
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