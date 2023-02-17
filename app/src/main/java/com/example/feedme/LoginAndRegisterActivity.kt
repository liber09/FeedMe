package com.example.feedme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginAndRegisterActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var emailView: EditText
    lateinit var passwordView: EditText
    lateinit var imageView: ImageView
    lateinit var imageViewLogo: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_and_register)


        auth = Firebase.auth
        emailView = findViewById(R.id.emailTextView)
        passwordView = findViewById(R.id.passwordTextView)
        imageView = findViewById(R.id.logoImageView)
        imageViewLogo = findViewById(R.id.feedMeLogoImageView)
        val regestreraRestaurant = findViewById<TextView>(R.id.tv_RegisterAsRestaurant)
        val registerDeliver = findViewById<TextView>(R.id.tv_registerDElivery)

        registerDeliver.setOnClickListener{

            val register = Intent(this,SignInDeliveryPerson::class.java)
            createUser()
            intent.putExtra("Email", "$emailView")
            startActivity(register)

        }

        regestreraRestaurant.setOnClickListener{
            val register = Intent(this,InfoRestaurantActivity::class.java)
            createUser()
            startActivity(register)

        }





        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val register = Intent(this,RegisterCustomerInfo::class.java)
            createUser()
            startActivity(register)

        }
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            loginUser()
            //Ifsats för olika former av users

            val customerLog = Intent(this,RestaurantViewActiviity::class.java)
            startActivity(customerLog)


        }
    }





    fun createUser() {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()



        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Register Complete", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun loginUser() {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        if (email.isEmpty() || password.isEmpty())
            return
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("!!!", "Sign in succeeded")
                    /*goToActivity()*/ // Kommer att länkas till första sidan när den är skapad efter att man loggat in
                } else {
                    Log.d("!!!", "user not signed in ${task.exception}")



                }
            }
        }
}














