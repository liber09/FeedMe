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
import androidx.core.widget.doOnTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginAndRegisterActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var emailView: EditText
    lateinit var passwordView: EditText
    lateinit var imageView: ImageView
    lateinit var imageViewLogo: ImageView
    lateinit var email: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_and_register)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)



        auth = Firebase.auth
        emailView = findViewById(R.id.textInputEditTextEmailLogReg)
        passwordView = findViewById(R.id.textInputEditTextPasswordLogReg)
        imageView = findViewById(R.id.logoImageView)
        imageViewLogo = findViewById(R.id.feedMeLogoImageView)
        val regestreraRestaurant = findViewById<TextView>(R.id.tv_RegisterAsRestaurant)
        val registerDeliver = findViewById<TextView>(R.id.tv_registerDElivery)
        passwordView.doOnTextChanged { text, start, before, count ->
            if (text!!.length < 6) {
                passwordView.error = "minst 6 siffror"
            }
        }


        registerDeliver.setOnClickListener {
            createDeliveryperson()

        }

        regestreraRestaurant.setOnClickListener {
            createRestaurant()
        }


        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {

            createCustomer()

            //customer@feed.me


        }
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {


            loginUser()



        }
    }


    fun createRestaurant() {
        email = emailView.text.toString()
        val password = passwordView.text.toString()  // Skapa restaurant konto

        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password)   // registrerar anv??ndaren med email + password, if sats om det ??r success eller inte
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Register Complete", Toast.LENGTH_SHORT).show()
                    val register = Intent(this, InfoRestaurantActivity::class.java)

                    register.putExtra("mail", email)
                    startActivity(register)


                } else {
                    Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()


                }
            }
    }

    fun createCustomer() {
        email = emailView.text.toString()   // Skapa konto f??r kund
        val password = passwordView.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Register Complete", Toast.LENGTH_SHORT).show()
                    val register = Intent(this, RegisterCustomerInfo::class.java)

                    register.putExtra("mail", email)
                    startActivity(register)
                } else {
                    Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()


                }
            }
    }


    fun createDeliveryperson() {
        email = emailView.text.toString()
        val password = passwordView.text.toString()     // Skapar konto f??r bud

        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Register Complete", Toast.LENGTH_SHORT).show()
                    val register = Intent(this, SignInDeliveryPerson::class.java)

                    register.putExtra("mail", email)
                    startActivity(register)

                } else {
                    Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()


                }
            }
    }

    fun loginUser() {
        email = emailView.text.toString()
        val password = passwordView.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser  // anv??ndaren man loggar in p??

                    if (user != null) {
                        val userId = user.uid

                        db.collection("customers").get()
                            .addOnSuccessListener { documents ->     // function som l??ser av om man registrerat sig som kund kopplat till databasen
                                for (document in documents) {
                                    if (document.id == "${user.uid}") {
                                        val intent = Intent(this, RestaurantViewActiviity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                            }



                                db.collection("users").document(userId)
                                    .collection("deliveryPersons").get()
                                    .addOnSuccessListener { result ->
                                        if (!result.isEmpty) {

                                            val intent =
                                                Intent(this, DeliveryPersonViewActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        } else {

                                            db.collection("restaurants").get()
                                                .addOnSuccessListener { documents ->
                                                    for (document in documents) {
                                                        if (document.id == "${user.uid}+1") {
                                                            val intent =
                                                                Intent(this, RestaurantDetailsActivity::class.java)
                                                            intent.putExtra("userId", userId)
                                                            intent.putExtra("restid", "${user.uid}+1")

                                                            startActivity(intent)
                                                            finish()
                                                        }
                                                    }
                                                }

                                        }

                                    }



                    }



                    Log.d("!!!", "Sign in succeeded")
                } else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()

                }


            }

    }
}
















