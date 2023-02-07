package com.example.feedme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val DISH_POSTION_KEY = "DISH_POSITION"
const val DiSH_POSITION_NOT_SET = -1

class AddNChangeFoodActivity : AppCompatActivity() {


    lateinit var dishNameET: EditText
    lateinit var descriptionET: EditText
    lateinit var foodCategorySpinner: Spinner
    lateinit var isGlutenFreeCB: CheckBox
    lateinit var isLaktoseFreeCB: CheckBox
    lateinit var isSeaFoodFreeCB: CheckBox
    lateinit var isVegeterianCB: CheckBox
    lateinit var isVeganCB: CheckBox
    lateinit var isEggFreeCB: CheckBox
    lateinit var isSoyFreeCB: CheckBox
    lateinit var isNutFreeCB: CheckBox
    lateinit var isLCHFCB: CheckBox
    lateinit var canBeMadeGlutenFreeCB: CheckBox
    lateinit var canBeMadeLaktoseFreeCB: CheckBox
    lateinit var canBeMadeVegeterianCB: CheckBox
    lateinit var canBeMadeVeganCB: CheckBox
    lateinit var normalPriceET: EditText
    lateinit var smalPriceET: EditText
    lateinit var largePriceET: EditText
    lateinit var glutenExtraCostET: EditText
    lateinit var laktosExtraCostET: EditText
    lateinit var veganExtraCostET: EditText
    lateinit var vegeterianExtraCostET: EditText
    lateinit var categoryOfDishString: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_nchange_food)






        dishNameET = findViewById(R.id.AddDishAdminFoodTitleEditText)
        descriptionET = findViewById(R.id.AddDishAdminFoodDescriptionLayoutEditText)
        foodCategorySpinner = findViewById(R.id.spinner_AddDishAdminTypeOfFood)
        isGlutenFreeCB = findViewById(R.id.cb_AddDishAdminisGlutenfree)
        isLaktoseFreeCB = findViewById(R.id.cb_AddDishAdminisLaktosfree)
        isSeaFoodFreeCB = findViewById(R.id.cb_AddDishAdminIsFreefromSeefood)
        isVeganCB = findViewById(R.id.cb_AddDishAdminIsVegan)
        isVegeterianCB = findViewById(R.id.cb_AddDishAdminIsVegeterian)
        isEggFreeCB = findViewById(R.id.cb_AddDishAdminIsEggFree)
        isSoyFreeCB = findViewById(R.id.cb_AddDishAdminisSoyFree)
        isNutFreeCB = findViewById(R.id.cb_AddDishAdminisNutFree)
        isLCHFCB = findViewById(R.id.cb_AddDishAdminIsLCHF)
        canBeMadeGlutenFreeCB = findViewById(R.id.cb_canBMadeGlutenfree)
        canBeMadeLaktoseFreeCB = findViewById(R.id.cb_CanBMadeLaktoseFree)
        canBeMadeVeganCB = findViewById(R.id.cb_canBMadeVegan)
        canBeMadeVegeterianCB = findViewById(R.id.cb_AddDishAdminCanBMadeVegeterian)
        normalPriceET = findViewById(R.id.ET_AddDishAdminCostsNormalSize)
        smalPriceET = findViewById(R.id.ET_AddDishAdminCostsSmalSize)
        largePriceET = findViewById(R.id.ET_AddDishAdminCostsLargelSize)
        glutenExtraCostET = findViewById(R.id.ET_AddDishAdminExtrakostGluten)
        laktosExtraCostET = findViewById(R.id.ET_AddDishAdminExtrakostLaktosFree)
        veganExtraCostET = findViewById(R.id.ET_AddDishAdminExtrakostVegan)
        vegeterianExtraCostET = findViewById(R.id.ET_AddDishAdminExtrakostVegeterian)

       // val dishPosition = intent.getIntExtra(DISH_POSTION_KEY, DiSH_POSITION_NOT_SET)



        val foodCategoryArray = arrayOf(
            "Huvudrätt", "Dessert", "Efterrätt"
        )

        val foodCategorySpinnerAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            foodCategoryArray

        )

        foodCategorySpinner.adapter = foodCategorySpinnerAdapter
        foodCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                categoryOfDishString = foodCategoryArray[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
                categoryOfDishString = foodCategoryArray[0]
            }

        }



        var saveDishBUTTON = findViewById<Button>(R.id.btn_AddDishToFirebase)
       /* if (dishPosition != DiSH_POSITION_NOT_SET) {
            displayDish(dishPosition)
            saveDishBUTTON.setOnClickListener{EditDish(dishPosition)}
        } else {*/
        saveDishBUTTON.setOnClickListener {
        AddDish() }
//}

    }

    //TODO rule only if  youre logged in as a restaurount - but works

    fun displayDish(position: Int) {

        val dish = DataManagerDishes.dishes[position]
        dishNameET.setText(dish.title)
        descriptionET.setText(dish.description)
        foodCategorySpinner.setAutofillHints(dish.category)

        //nedan funkar inte fullt ut det är inte inbockat

        if (dish.isGlutenFree == true) {
            isGlutenFreeCB.isChecked
        }
        if (dish.isLaktoseFree == true) {
            isLaktoseFreeCB.isChecked
        }
        if (dish.isVegan == true) {
            isVeganCB.isChecked
        }
        if (dish.isVegetarian == true) {
            isVegeterianCB.isChecked
        }
        if (dish.isEggFree == true) {
            isEggFreeCB.isChecked
        }
        if (dish.isSoyfree == true) {
            isSoyFreeCB.isChecked
        }
        if (dish.isFreeFromSeeFood == true) {
            isSeaFoodFreeCB.isChecked
        }
        if (dish.isLCHF == true) {
            isLCHFCB.isChecked
        }
        if (dish.canBeMadeVegan == true) {
            canBeMadeVeganCB.isChecked
        }
        if (dish.canBeMadeVegeterian == true) {
            canBeMadeVegeterianCB.isChecked
        }
        if (dish.canBeMadeLaktosFree == true) {
            canBeMadeLaktoseFreeCB.isChecked
        }
        if (dish.canBeMadeGlutenFree == true) {
            canBeMadeGlutenFreeCB.isChecked
        }

        if (laktosExtraCostET.text.toString() != null) {
            laktosExtraCostET.setText(dish.extraCostLaktose.toString())
        }
        if (glutenExtraCostET.text.toString() != null) {
            glutenExtraCostET.setText(dish.extraCostGluten.toString())
        }
        if (veganExtraCostET.text.toString() != null) {
            laktosExtraCostET.setText(dish.extraCostVegan.toString())
        }
        if (vegeterianExtraCostET.text.toString() != null) {
            vegeterianExtraCostET.setText(dish.extraCostVegeterian.toString())
        }
        if (smalPriceET.text.toString() != null) {
            smalPriceET.setText(dish.priceSmallPortion.toString())
        }
        if (normalPriceET.text.toString() != null) {
            normalPriceET.setText(dish.priceNormalPortion.toString())
        }
        if (largePriceET.text.toString() != null) {
            largePriceET.setText(dish.priceLargePortion.toString())
        }


    }
// TODO - edit through firebase - only possible local
    fun EditDish(position: Int){

       // val restaurant = auth.currentUser



        DataManagerDishes.dishes[position].title =dishNameET.text.toString()
        DataManagerDishes.dishes[position].description = descriptionET.text.toString()
        DataManagerDishes.dishes[position].isGlutenFree = false
        if(isGlutenFreeCB.isChecked){
            DataManagerDishes.dishes[position].isGlutenFree = true
        }
        DataManagerDishes.dishes[position].isLCHF = false
        if(isLCHFCB.isChecked){
            DataManagerDishes.dishes[position].isLCHF = true
        }
        DataManagerDishes.dishes[position].isSoyfree = false
        if(isSoyFreeCB.isChecked){
            DataManagerDishes.dishes[position].isSoyfree = true
        }
        DataManagerDishes.dishes[position].isEggFree = false
        if(isEggFreeCB.isChecked){
            DataManagerDishes.dishes[position].isEggFree = true
        }
        DataManagerDishes.dishes[position].isVegan= false
        if(isVeganCB.isChecked){
            DataManagerDishes.dishes[position].isVegan = true
        }
        DataManagerDishes.dishes[position].isFreeFromSeeFood = false
        if(isSeaFoodFreeCB.isChecked){
            DataManagerDishes.dishes[position].isFreeFromSeeFood = true
        }
        DataManagerDishes.dishes[position].isLaktoseFree= false
        if(isLaktoseFreeCB.isChecked){
            DataManagerDishes.dishes[position].isLaktoseFree = true
        }
        DataManagerDishes.dishes[position].isNutfree= false
        if(isNutFreeCB.isChecked){
            DataManagerDishes.dishes[position].isNutfree = true
        }
        DataManagerDishes.dishes[position].isVegetarian = false
        if(isVegeterianCB.isChecked){
            DataManagerDishes.dishes[position].isVegetarian = true
        }

        DataManagerDishes.dishes[position].canBeMadeVegan= false
        if(canBeMadeVeganCB.isChecked){
            DataManagerDishes.dishes[position].canBeMadeVegan = true
        }
        DataManagerDishes.dishes[position].canBeMadeVegeterian= false
        if(canBeMadeVegeterianCB.isChecked){
            DataManagerDishes.dishes[position].canBeMadeVegeterian = true
        }
        DataManagerDishes.dishes[position].canBeMadeGlutenFree= false
        if(canBeMadeGlutenFreeCB.isChecked){
            DataManagerDishes.dishes[position].canBeMadeGlutenFree = true
        }
        DataManagerDishes.dishes[position].canBeMadeLaktosFree = false
        if(canBeMadeLaktoseFreeCB.isChecked){
            DataManagerDishes.dishes[position].canBeMadeLaktosFree = true
        }
        DataManagerDishes.dishes[position].extraCostVegan == 0.0
        if (veganExtraCostET.text.toString() != "0" && veganExtraCostET.text.toString() != "null"&& veganExtraCostET.text.toString() != "0.0") {
            DataManagerDishes.dishes[position].extraCostVegan =veganExtraCostET.text.toString().toDouble()
        }

        DataManagerDishes.dishes[position].extraCostVegeterian ==  0.0
        if (vegeterianExtraCostET.text.toString() != "0" && vegeterianExtraCostET.text.toString() != "null" &&vegeterianExtraCostET.text.toString() != "0.0" ) {
            DataManagerDishes.dishes[position].extraCostVegeterian = vegeterianExtraCostET.text.toString().toDouble()
        }

        DataManagerDishes.dishes[position].extraCostGluten ==  0.0
        if (glutenExtraCostET.text.toString() != "0"
            && glutenExtraCostET.text.toString() != "null" &&glutenExtraCostET.text.toString() != "0.0") {
         if(glutenExtraCostET.text.toString().contains(",")){
             // ToDo right syntax to change to .
         }

            try { DataManagerDishes.dishes[position].extraCostGluten = glutenExtraCostET.text.toString().toDouble()}
         catch (e: java.lang.NumberFormatException) {
             Toast.makeText( applicationContext,"Only numbers allowed", Toast.LENGTH_SHORT).show()
             DataManagerDishes.dishes[position].extraCostGluten ==0.0

         }


        }

        DataManagerDishes.dishes[position].extraCostLaktose ==  0.0
        if (laktosExtraCostET.text.toString() != "0" && laktosExtraCostET.text.toString() != "null" && laktosExtraCostET.text.toString() != "0.0" ) {
            DataManagerDishes.dishes[position].extraCostLaktose =laktosExtraCostET.text.toString().toDouble()
        }

        DataManagerDishes.dishes[position].priceSmallPortion ==  0.0
        if (smalPriceET.text.toString() != "-" && smalPriceET.text.toString() != "null" && smalPriceET.text.toString() != "0.0" ) {
            DataManagerDishes.dishes[position].priceSmallPortion = smalPriceET.text.toString().toDouble()
        }
        DataManagerDishes.dishes[position].priceLargePortion ==  0.0
        if (largePriceET.text.toString() != "-"&&largePriceET.text.toString() != "0.0" && largePriceET.text.toString() != "null") {
            DataManagerDishes.dishes[position].priceLargePortion = largePriceET.text.toString().toDouble()
        }
        DataManagerDishes.dishes[position].priceNormalPortion = 0.0

        if (normalPriceET.text.toString() != "-"&& normalPriceET.text.toString() !="null"&& normalPriceET.text.toString() !="0.0") {
            DataManagerDishes.dishes[position].priceSmallPortion == normalPriceET.text.toString().toDouble()
        }
        finish()


    }


    fun AddDish() {

        // TODO when restaurants are logged in
       /* var auth = Firebase.auth

        val restaurant = auth.currentUser
        if (restaurant == null){
            return
        } */


        val dishName = dishNameET.text.toString()
        val description = descriptionET.text.toString()
        val categoryDish = categoryOfDishString
        var priceChosen = true

        var isglutenfree = false
        if (isGlutenFreeCB.isChecked) {
            isglutenfree = true
        }
        var islaktosefree = false
        if (isLaktoseFreeCB.isChecked) {
            islaktosefree = true
        }
        var isSeeFoodfree = false
        if (isSeaFoodFreeCB.isChecked) {
            isSeeFoodfree = true
        }
        var isVegan = false
        if (isVeganCB.isChecked) {
            isVegan = true
        }
        var isVegeterian = false
        if (isVegeterianCB.isChecked) {
            isVegeterian = true
        }
        var isEggfree = false
        if (isEggFreeCB.isChecked) {
            isEggfree = true
        }
        var isSoyfree = false
        if (isSoyFreeCB.isChecked) {
            isSoyfree = true
        }
        var isNutFree = false
        if (isNutFreeCB.isChecked) {
            isNutFree = true
        }
        var isLCHF = false
        if (isLCHFCB.isChecked) {
            isLCHF = true
        }
        // TODO: add logic that only if it is not glutnfree etc
        //  from the beginning the checkbox appears

        //TODO: add logic to accept only numbers
        var canBeMadeGlutenfree = false
        if (canBeMadeGlutenFreeCB.isChecked) {
            canBeMadeGlutenfree = true
        }

        var extraCostGluten: Double? = null
        if (glutenExtraCostET.text.toString() != "0" || glutenExtraCostET.text.toString() != "null") {
            extraCostGluten = glutenExtraCostET.text.toString().toDouble()
        }
        var canBeMadeLaktosefree = false
        if (canBeMadeLaktoseFreeCB.isChecked) {
            canBeMadeLaktosefree = true
        }

        var extraCostLaktose: Double? = null
        if (laktosExtraCostET.text.toString() != "0" || laktosExtraCostET.text.toString() != null ) {
            extraCostLaktose = laktosExtraCostET.text.toString().toDouble()
        }
        var canBeMadeVegeterian = false
        if (canBeMadeVegeterianCB.isChecked) {
            canBeMadeVegeterian = true
        }

        var extraCostVegeterian: Double? = null
        if (vegeterianExtraCostET.text.toString() != "0" || laktosExtraCostET.text.toString() != "null") {
            extraCostVegeterian = vegeterianExtraCostET.text.toString().toDouble()
        }

        var canBeMadeVegan = false
        if (canBeMadeVeganCB.isChecked) {
            canBeMadeVegan = true
        }

        var extraCostVegan: Double? = null
        if (veganExtraCostET.text.toString() != "0") {
            extraCostVegan = veganExtraCostET.text.toString().toDouble()
        }

        var priceNormalPortion: Double? = null
        if (normalPriceET.text.toString() != "-") {
            priceNormalPortion = normalPriceET.text.toString().toDouble()
        }
        var priceSmalPortion: Double? = null
        if (smalPriceET.text.toString() != "-") {
            priceSmalPortion = smalPriceET.text.toString().toDouble()
        }

        var priceLargePortion: Double? = null
        if (largePriceET.text.toString() != "-") {
            priceLargePortion = largePriceET.text.toString().toDouble()
        }


        if (dishName.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Rättens namn får inte vara tomt",
                Toast.LENGTH_LONG
            ).show()
        }
        //add Logic - only spara if price is angiven av minst en storlek

        //TODO rule that you cannot add if you write something in a field which is for numbers

        if ((priceLargePortion == null || priceLargePortion == 0.0) && (priceSmalPortion == null ||priceSmalPortion == 0.0)
            && (priceNormalPortion == null || priceNormalPortion == 0.0)
        ) {
            Toast.makeText(
                applicationContext,
                "Minst 1 pris för 1 storlek måste vara angiven",
                Toast.LENGTH_LONG
            ).show()
            priceChosen == false
        }
        if (dishName.isNotEmpty() && priceChosen == true) {
            val newDish = Dishes(
                dishName,
                description,
                priceNormalPortion,
                priceSmalPortion,
                priceLargePortion,
                isglutenfree,
                islaktosefree,
                isEggfree,
                isNutFree,
                isSeeFoodfree,
                isSoyfree,
                isLCHF,
                isVegeterian,
                isVegan,
                categoryDish,
                canBeMadeGlutenfree,
                canBeMadeLaktosefree,
                canBeMadeVegan,
                canBeMadeVegeterian,
                extraCostGluten,
                extraCostLaktose,
                extraCostVegan,
                extraCostVegeterian,
                ""
            )

           // DataManagerDishes.dishes.add(newDish) only local
            db.collection("restaurants").document("restaurant2").collection("dishes").add(newDish)

            finish()

        }


    }
}