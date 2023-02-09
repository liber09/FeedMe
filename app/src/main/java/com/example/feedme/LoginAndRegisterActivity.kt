package com.example.feedme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageView
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

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val register = Intent(this,RegisterActivity::class.java)
            startActivity(register)
        }
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            loginUser()
        }
    }

    /*fun goToActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent) */ //  Kommer att länkas till första sidan när den är skapad efter att man loggat in

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









