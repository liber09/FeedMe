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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_delivery_person)
        intent.getStringExtra("email")



       val save = findViewById<Button>(R.id.btn_saveDelivery)

        save.setOnClickListener{
            saveDeliveryPerson()

            val goToDeliveryPView= Intent(this,DeliveryPersonViewActivity::class.java)


            startActivity(goToDeliveryPView)





        }






    }

    fun saveDeliveryPerson () {


        val firstName = findViewById<EditText>(R.id.TInp_et_firstname).text.toString()
        val lastname = findViewById<EditText>(R.id.TInp_et_lastName).text.toString()
        val employer = findViewById<EditText>(R.id.TInp_et_employe).text.toString()
        val zipCode = findViewById<EditText>(R.id.TInp_et_zipkode).text.toString()
        val city = findViewById<EditText>(R.id.TInp_et_city).text.toString()
        val email = findViewById<EditText>(R.id.TInp_et_email).text.toString()
        val  telephone = findViewById<EditText>(R.id.TInp_et_telephone).text.toString()


        val deliveryPerson = DeliveryPerson(
                firstName,lastname,employer,city,zipCode,telephone,email

            )
        if (firstName.isNotEmpty()&&lastname.isNotEmpty()&&employer.isNotEmpty()
            &&zipCode.isNotEmpty()&&city.isNotEmpty()&& telephone.isNotEmpty()
            &&email.isNotEmpty())
        {

            db.collection("deliveryPersons").add(deliveryPerson)
        }
        else {
            Toast.makeText(this, "fyll i alla fält", Toast.LENGTH_SHORT).show()

        }

    }
}