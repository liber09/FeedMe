package com.example.feedme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.feedme.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var emailView: EditText
    lateinit var passwordView: EditText
    var db = Firebase.firestore
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val registerButton = findViewById<Button>(R.id.registerButton1)
        registerButton.setOnClickListener {
            // Create user with email + password
            createUser()

            val saveButton = findViewById<Button>(R.id.saveButton)
            saveButton.setOnClickListener {
                saveUserDataBase()

            }


        }
    }


    fun goToActivity() {

        val intent1 = Intent(this, LoginAndRegisterActivity::class.java)
        startActivity(intent1)
    }

        fun saveUserDataBase() {
            val firstName = findViewById<EditText>(R.id.namnTextView).text.toString()
            val lastName = findViewById<EditText>(R.id.efterNamnTextView).text.toString()
            val email = findViewById<EditText>(R.id.emailTextView1).text.toString()
            val password = findViewById<EditText>(R.id.passwordTextView1).text.toString()
            val userType = findViewById<EditText>(R.id.userTextView)

            val user = User(
                firstName,
                lastName,
                email,
                password,
                userType.toString(),
                )
            db.collection("users").add(user)
            Toast.makeText(this, getString(R.string.saveSuccess), Toast.LENGTH_SHORT).show()


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
                            Log.d("!!!", "Created user succeeded")
                            goToActivity()
                        } else {
                            Log.d("!!!", "user not created")
                        }
                    }
            }
        }







