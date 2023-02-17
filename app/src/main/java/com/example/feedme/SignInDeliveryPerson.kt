package com.example.feedme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.set
import com.example.feedme.data.Customer
import com.example.feedme.data.DeliveryPerson

class SignInDeliveryPerson : AppCompatActivity() {
    lateinit var firstName: String
    lateinit var lastname: String
    lateinit var employer: String
    lateinit var zipCode: String
    lateinit var city: String
    lateinit var email: String
    lateinit var telephone: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_delivery_person)
        intent.getStringExtra("email")

        firstName = findViewById<EditText>(R.id.TInp_et_firstname).text.toString()
        lastname = findViewById<EditText>(R.id.TInp_et_lastName).text.toString()
        employer = findViewById<EditText>(R.id.TInp_et_employe).text.toString()
        zipCode = findViewById<EditText>(R.id.TInp_et_zipkode).text.toString()
        city = findViewById<EditText>(R.id.TInp_et_city).text.toString()
        email = findViewById<EditText>(R.id.TInp_et_email).text.toString()
        telephone = findViewById<EditText>(R.id.TInp_et_telephone).text.toString()


       val save = findViewById<Button>(R.id.btn_saveDelivery)

        save.setOnClickListener{
            saveDeliveryPerson()

            val goToDeliveryPView= Intent(this,DeliveryPersonViewActivity::class.java)


            startActivity(goToDeliveryPView)





        }






    }

    fun saveDeliveryPerson () {

        if (firstName.isNotEmpty()&&lastname.isNotEmpty()&&employer.isNotEmpty()
            &&zipCode.isNotEmpty()&&city.isNotEmpty()&& telephone.isNotEmpty()
            &&email.isNotEmpty())
        {
            val deliveryPerson = DeliveryPerson(
                firstName,lastname,employer,zipCode,city,telephone

            )
            db.collection("deliveryPersons").add(deliveryPerson)
        }
        else {
            Toast.makeText(this, "fyll i alla fält", Toast.LENGTH_SHORT).show()

        }

    }
}