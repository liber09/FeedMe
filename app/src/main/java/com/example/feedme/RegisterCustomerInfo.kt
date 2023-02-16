package com.example.feedme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.feedme.data.Customer
import com.google.firebase.firestore.SetOptions

class RegisterCustomerInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_customer_info)

        //Get save button
        val btnSaveCustomerInfo = findViewById<Button>(R.id.btnSaveCustomerInfo)
        var update = false
        if(intent.hasExtra("USERNAME")) {
            update = true
            btnSaveCustomerInfo.setText("Update")

            var customer: Customer? = null
            var docName : String = ""
            db.collection("customers")
                .whereEqualTo("userName", intent.getStringExtra("USERNAME"))
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        customer = document.toObject(Customer::class.java)
                        Log.v("!!!", document.id)//docName = document.id
                        loadCustomer(customer!!)
                    }
                }


        }
        //Set clickListener
        btnSaveCustomerInfo.setOnClickListener {
            //Call function to save customer info to database
            if(!update) {
                saveCustomerInfoToDatabase()
            } else {
                db.collection("customers")
                    .whereEqualTo("userName", findViewById<EditText>(R.id.textInputEditTextUserName).text.toString())
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            if(documents.size() == 1) {
                                saveCustomerInfoToDatabase(document.id)
                            }
                        }
                    }
                    .addOnFailureListener { exception ->

                    }

            }

        }
    }

    //Function that saves the information the customer has entered as a new customer user in the database
    private fun saveCustomerInfoToDatabase(documentId : String = "") {
        //First, validate if input is correct
        if (validateInput()){
            val firstName = findViewById<EditText>(R.id.textInputEditTextFirstname).text.toString()
            val lastName = findViewById<EditText>(R.id.textInputEditTextLastname).text.toString()
            val address = findViewById<EditText>(R.id.textInputEditTextAddress).text.toString()
            val postalCode = findViewById<EditText>(R.id.textInputEditTextPostalCode).text.toString()
            val city = findViewById<EditText>(R.id.textInputEditTextCity).text.toString()
            val phoneNumber = findViewById<EditText>(R.id.textInputEditTextPhoneNumber).text.toString()
            val eMail = findViewById<EditText>(R.id.textInputEditTextEmail).text.toString()
            val userName = findViewById<EditText>(R.id.textInputEditTextUserName).text.toString()
            val allergies = findViewById<EditText>(R.id.textInputEditTextALlergies).text.toString()

            //Create a new customer object to save to database
            val customer = Customer(
                firstName,
                lastName,
                address,
                postalCode,
                city,
                eMail,
                phoneNumber,
                "customer", //Hardcoded usertype
                userName,
                allergies
            )
            //Add user to users collection
            if(documentId == "") {
                db.collection("customers").add(customer) //Add restaurant to database
            }
            else {
                db.collection("customers").document(documentId).set(customer, SetOptions.merge())
            }
            //Tell user save was successful
            Toast.makeText(this, getString(R.string.saveSuccess), Toast.LENGTH_SHORT).show()
        }else {
            //Input was not correct, give user a ,message to correct and try again
            Toast.makeText(this, getString(R.string.wrongInput), Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadCustomer(customer : Customer) {

        if (customer != null) {
            findViewById<EditText>(R.id.textInputEditTextFirstname).setText(customer!!.firstName)
            findViewById<EditText>(R.id.textInputEditTextLastname).setText(customer!!.lastName)
            findViewById<EditText>(R.id.textInputEditTextAddress).setText(customer!!.address)
            findViewById<EditText>(R.id.textInputEditTextPostalCode).setText(customer!!.postalCode)
            findViewById<EditText>(R.id.textInputEditTextCity).setText(customer!!.city)
            findViewById<EditText>(R.id.textInputEditTextPhoneNumber).setText(customer!!.phoneNumber)
            findViewById<EditText>(R.id.textInputEditTextEmail).setText(customer!!.eMail)
            findViewById<EditText>(R.id.textInputEditTextUserName).isEnabled = false
            findViewById<EditText>(R.id.textInputEditTextUserName).setText(customer!!.userName)
            findViewById<EditText>(R.id.textInputEditTextALlergies).setText(customer!!.allergies)
        }

    }

    //Check if any fields are empty except for allergies and userName which can be empty
    private fun validateInput():Boolean {
        var isInputOk = true //Set to true first, change to false if anything is not correct
        val firstName = findViewById<EditText>(R.id.textInputEditTextFirstname).text.toString()
        val lastName = findViewById<EditText>(R.id.textInputEditTextLastname).text.toString()
        val address = findViewById<EditText>(R.id.textInputEditTextAddress).text.toString()
        val postalCode = findViewById<EditText>(R.id.textInputEditTextPostalCode).text.toString()
        val city = findViewById<EditText>(R.id.textInputEditTextCity).text.toString()
        val phoneNumber = findViewById<EditText>(R.id.textInputEditTextPhoneNumber).text.toString()
        val eMail = findViewById<EditText>(R.id.textInputEditTextEmail).text.toString()
        if(firstName.isNullOrEmpty()){
            isInputOk = false
        }
        if(lastName.isNullOrEmpty()){
            isInputOk = false
        }
        if(address.isNullOrEmpty()){
            isInputOk = false
        }
        if(postalCode.isNullOrEmpty() && isNumericToX(postalCode)){
            isInputOk = false
        }
        if(city.isNullOrEmpty()){
            isInputOk = false
        }
        if(phoneNumber.isNullOrEmpty()){
            isInputOk = false
        }
        if(eMail.isNullOrEmpty()){
            isInputOk = false
        }
        return isInputOk
    }

    //Function that checks if a string contains numbers only. Returns false if not numeric.
    private fun isNumericToX(toCheck: String): Boolean {
        return toCheck.toDoubleOrNull() != null
    }
}