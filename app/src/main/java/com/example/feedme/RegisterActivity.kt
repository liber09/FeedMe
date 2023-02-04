package com.example.feedme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var emailView: EditText
    lateinit var passwordView: EditText
    lateinit var nameView: EditText
    lateinit var lastnameView: EditText
    lateinit var checkBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        emailView = findViewById(R.id.emailTextView1)
        passwordView = findViewById(R.id.passwordTextView1)
        nameView = findViewById(R.id.namnTextView)
        lastnameView = findViewById(R.id.efterNamnTextView)
        checkBox = findViewById(R.id.adminCheckBox)
        checkBox = findViewById(R.id.kundCheckBox)
        checkBox = findViewById(R.id.budCheckBox)

        val registerButton = findViewById<Button>(R.id.registerButton1)
        registerButton.setOnClickListener {
            createUser()

        }
    }
            fun goToActivity() {
            val intent1 = Intent(this, LoginAndRegisterActivity::class.java)
                startActivity(intent1)
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







