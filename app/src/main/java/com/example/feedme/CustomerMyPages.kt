package com.example.feedme

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.feedme.data.Customer
import com.example.feedme.data.Order
import com.google.firebase.firestore.ktx.toObject
import org.w3c.dom.Text

class CustomerMyPages : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_my_pages)
        val id = intent.getStringExtra("CUSTOMER_DOCUMENTID")
        val customer = getCustomerByDocumentId(id.toString())
    }

    fun getCustomerByDocumentId(customerId: String):Customer?{
        var customer: Customer? = null
        db.collection("customer")
            .whereEqualTo("documentId", customerId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    customer = document.toObject<Customer>()
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
        return customer
    }

    fun updateViewsWithInfo(customer:Customer){
        val TVMyPagesFirstName = findViewById<TextView>(R.id.TVMyPagesFirstName)
        val TVMyPagesLastName = findViewById<TextView>(R.id.TVMyPagesLastName)
        val TVMyPagesAddress = findViewById<TextView>(R.id.TVMyPagesAddress)

    }
}