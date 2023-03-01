package com.example.feedme

import Drink
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AddNChangeDrinks : AppCompatActivity() {
    lateinit var drinkNameET: EditText
    lateinit var drinkCategoryET: EditText
    lateinit var drinkPriceET: EditText
    lateinit var drinkSizeET: EditText
    lateinit var auth: FirebaseAuth
    lateinit var restaurantIdent :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_nchange_drinks)

        auth = Firebase.auth
        restaurantIdent = intent.getStringExtra("resid").toString()

        drinkNameET = findViewById(R.id.AddDrinkEditText)
        drinkCategoryET = findViewById(R.id.AddDrinkTypeText)
        drinkPriceET = findViewById(R.id.DrinkPriceEditText)
        drinkSizeET= findViewById(R.id.DrinkSizeEditText)


        val addDrinkButton = findViewById<Button>(R.id.btn_addDrinkAdmin)
        addDrinkButton.setOnClickListener { AddDrink()
       }
        val finishButton = findViewById<Button>(R.id.btn_finishAddDrinks)
        finishButton.setOnClickListener { finish() }
        val deletButton = findViewById<ImageButton>(R.id.iB_deleteDrink)


    }
    fun AddDrink() {
        val drinkName = drinkNameET.text.toString()
        val drinkCategory = drinkCategoryET.text.toString()
        val drinkPrice = drinkPriceET.text.toString().toDouble()
        val drinkSize = drinkSizeET.text.toString()

        if(drinkName.isNotEmpty()&&drinkPrice != null &&drinkSize.isNotEmpty()){
            val newDrink = Drink("",drinkName,drinkSize,drinkCategory,drinkPrice)


        db.collection("restaurants")
            .document(restaurantIdent)
            .collection("drinks")
            .add(newDrink)

            Toast.makeText(
                applicationContext,
                "Sparat",
                Toast.LENGTH_LONG
            ).show()
            drinkCategoryET.setText("")
            drinkNameET.setText("")
            drinkPriceET.setText("")
            drinkSizeET.setText("")


    } else{
            Toast.makeText(
                applicationContext,
                "Namn,pris och storlek måste vara ifylld",
                Toast.LENGTH_LONG
            ).show()
    }


    }

}