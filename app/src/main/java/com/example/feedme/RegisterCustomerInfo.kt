package com.example.feedme

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.feedme.MyPagesCustomer.customer
import com.example.feedme.data.Customer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object NewCustomer{
    var customerId: String = ""
}

class RegisterCustomerInfo : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_customer_info)
        var emailFromSignUp = intent.getStringExtra("mail").toString()
        var eMail = findViewById<EditText>(R.id.textInputEditTextEmail)
        eMail.setText(emailFromSignUp)
        auth = Firebase.auth

        //Get save button
        val btnSaveCustomerInfo = findViewById<Button>(R.id.btnSaveCustomerInfo)

        var update = false
        if(intent.hasExtra("ID")) {
            val documentId = intent.getStringExtra("ID") ?: ""

            val user = auth.currentUser
            var cust: Customer? = null

            if(user != null)
                db.collection("users")
                    .document(user.uid)
                    .collection("customers").document(documentId).get()
                    .addOnSuccessListener { document ->
                        cust = document.toObject(Customer::class.java)
                        btnSaveCustomerInfo.setText("Update")
                        update = true
                        loadCustomer(cust!!)
                    }

        }

        //Set clickListener
        btnSaveCustomerInfo.setOnClickListener {
            //Call function to save customer info to database
            if(!update) {
                saveCustomerInfoToDatabase()
            } else {
                saveCustomerInfoToDatabase(intent.getStringExtra("ID") ?: "")
            }
        }
        val customerInfoBack = findViewById<ImageView>(R.id.customerInfoBack)
        val customerInfoHome = findViewById<ImageView>(R.id.customerInfoHome)
        val logo = findViewById<ImageView>(R.id.iv_Logo)
        logo.setOnClickListener{
            val intent= Intent(this,RestaurantViewActiviity::class.java)
            startActivity(intent)
        }
        customerInfoHome.setOnClickListener{
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        customerInfoBack.setOnClickListener{
            finish()
        }
    }

    //Function that saves the information the customer has entered as a new customer user in the database
    private fun saveCustomerInfoToDatabase(docId : String = "") {

        val user = auth.currentUser
        /*if(user == null ){
            return
        }*/
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
                city,
                postalCode,
                phoneNumber,
                eMail,
                "customer", //Hardcoded usertype
                allergies,
                userName,
                customerNumber = (DataManagerCustomers.customers.count()+1).toString()

            )
            //Add user to users collection
            val newItemRef = if(docId.isEmpty()) db.collection("customers").document().id else docId
            customer.customerId = newItemRef.toString()
            NewCustomer.customerId = newItemRef.toString()
            MyPagesCustomer.customer = customer

            if (user != null){
                db.collection("users")
                    .document(user.uid)
                    .collection("customers").document(newItemRef.toString()).set(customer) //Add customer to database
                Toast.makeText(this, getString(R.string.saveSuccess), Toast.LENGTH_SHORT).show()
                val intent= Intent(this,CustomerMyPages::class.java)
                intent.putExtra("CUSTOMER_DOCUMENTID",newItemRef.toString())
                startActivity(intent)

            }
            else{

            db.collection("customers").document(newItemRef.toString()).set(customer) //Add customer to database
            //Tell user save was successful
            Toast.makeText(this, getString(R.string.saveSuccess), Toast.LENGTH_SHORT).show()
            val intent= Intent(this,CustomerMyPages::class.java)
            intent.putExtra("CUSTOMER_DOCUMENTID",newItemRef.toString())
            startActivity(intent)}
        }else {
            //Input was not correct, give user a ,message to correct and try again
            Toast.makeText(this, getString(R.string.wrongInput), Toast.LENGTH_SHORT).show()
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
}