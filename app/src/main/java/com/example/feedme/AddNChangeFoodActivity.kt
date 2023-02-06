package com.example.feedme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class AddNChangeFoodActivity : AppCompatActivity() {

   // lateinit var db :FirebaseFirestore
    lateinit var dishNameET : EditText
    lateinit var descriptionET : EditText
    lateinit var spinnerCategory : Spinner
    lateinit var isGlutenFreeCB : CheckBox
    lateinit var isLaktoseFreeCB : CheckBox
    lateinit var isSeaFoodFreeCB : CheckBox
    lateinit var isVegeterianCB : CheckBox
    lateinit var isVeganCB : CheckBox
    lateinit var isEggFreeCB : CheckBox
    lateinit var isSoyFreeCB : CheckBox
    lateinit var isNutFreeCB : CheckBox
    lateinit var canBeMadeGlutenFreeCB : CheckBox
    lateinit var canBeMadeLaktoseFreeCB : CheckBox
    lateinit var canBeMadeVegeterianCB : CheckBox
    lateinit var canBeMadeVeganCB : CheckBox
    lateinit var normalPriceET : EditText
    lateinit var smalPriceET : EditText
    lateinit var largePriceET : EditText
    lateinit var glutenExtraCostET : EditText
    lateinit var laktosExtraCostET : EditText
    lateinit var veganExtraCostET : EditText
    lateinit var vegeterianExtraCostET : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_nchange_food)

        dishNameET = findViewById(R.id.AddDishAdminFoodTitleEditText)
        descriptionET = findViewById(R.id.AddDishAdminFoodDescriptionLayoutEditText)
        spinnerCategory = findViewById(R.id.spinner_AddDishAdminTypeOfFood)
        isGlutenFreeCB = findViewById(R.id.cb_AddDishAdminisGlutenfree)
        isLaktoseFreeCB = findViewById(R.id.cb_AddDishAdminisLaktosfree)
        isSeaFoodFreeCB = findViewById(R.id.cb_AddDishAdminIsFreefromSeefood)
        isVeganCB = findViewById(R.id.cb_AddDishAdminIsVegan)
        isVegeterianCB = findViewById(R.id.cb_AddDishAdminIsVegeterian)
        isEggFreeCB = findViewById(R.id.cb_AddDishAdminIsEggFree)
        isSoyFreeCB = findViewById(R.id.cb_AddDishAdminisSoyFree)
        isNutFreeCB = findViewById(R.id.cb_AddDishAdminisNutFree)
        canBeMadeGlutenFreeCB = findViewById(R.id.cb_canBMadeGlutenfree)
        canBeMadeLaktoseFreeCB= findViewById(R.id.cb_CanBMadeLaktoseFree)
        canBeMadeVeganCB= findViewById(R.id.cb_canBMadeVegan)
        canBeMadeVegeterianCB= findViewById(R.id.cb_AddDishAdminCanBMadeVegeterian)
        normalPriceET = findViewById(R.id.ET_AddDishAdminCostsNormalSize)
        smalPriceET = findViewById(R.id.ET_AddDishAdminCostsSmalSize)
        largePriceET = findViewById(R.id.ET_AddDishAdminCostsLargelSize)
        glutenExtraCostET = findViewById(R.id.ET_AddDishAdminExtrakostGluten)
        laktosExtraCostET = findViewById(R.id.ET_AddDishAdminExtrakostLaktosFree)
        veganExtraCostET = findViewById(R.id.ET_AddDishAdminExtrakostVegan)
        vegeterianExtraCostET = findViewById(R.id.ET_AddDishAdminExtrakostVegeterian)









    }
}