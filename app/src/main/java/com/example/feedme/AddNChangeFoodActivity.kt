package com.example.feedme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.feedme.data.Dishes
import com.example.feedme.data.Restaurant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

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
    lateinit var dishImage: ImageView
    lateinit var restaurantIdent :String
    lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_nchange_food)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        auth = Firebase.auth


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
        dishImage = findViewById(R.id.iV_AddDishAdminUploadFoodPic)

        restaurantIdent = intent.getStringExtra("resid").toString()

       val dishPosition = intent.getIntExtra(DISH_POSTION_KEY, DiSH_POSITION_NOT_SET)


        val cancelBtn = findViewById<Button>(R.id.btn_Cancel_addFood)
        cancelBtn.setOnClickListener { finish() }

        val deleteBtn = findViewById<ImageButton>(R.id.btn_deleteAddNchn)
        deleteBtn.isInvisible = true

        if (dishPosition != DiSH_POSITION_NOT_SET)   {
            deleteBtn.isVisible = true


        deleteBtn.setOnClickListener { db.collection("restaurants")
            .document(restaurantIdent)
            .collection("dishes")
            .document(DataManagerDishes.dishes[dishPosition].documentId!!).delete()
            .addOnSuccessListener { Log.d("ddd", "DocumentSnapshot sucessfully deleted!") }
        finish()
        }
        }




        val foodCategoryArray = arrayOf(
            "Huvudrätt", "Förrätt", "Efterrätt"
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

            }

        }



        val saveDishBUTTON = findViewById<Button>(R.id.btn_AddDishToFirebase)
        if (dishPosition != DiSH_POSITION_NOT_SET) {
            displayDish(dishPosition)
            saveDishBUTTON.setOnClickListener{EditDish(dishPosition)}
        } else {
        saveDishBUTTON.setOnClickListener {
        AddDish() }


   }
    }



    fun displayDish(position: Int) {

        val dish = DataManagerDishes.dishes[position]
        dishNameET.setText(dish.title)
        descriptionET.setText(dish.description)
        foodCategorySpinner.setAutofillHints(dish.category)


        if (dish.dishImagePath.isNotEmpty()){

        val imageref = Firebase.storage.reference.child(dish.dishImagePath)
        imageref.downloadUrl.addOnSuccessListener { Uri ->
            val imageURL = Uri.toString() // get the URL for the image
            //Use third party product glide to load the image into the imageview
            Glide.with(this)
                .load(imageURL)
                .into( dishImage)
        }}

        if (dish.isGlutenFree == true) {
            isGlutenFreeCB.isChecked = true
        }
        if (dish.isLaktoseFree == true) {

            isLaktoseFreeCB.isChecked =true
        }
        if (dish.isVegan == true) {
            isVeganCB.isChecked = true
        }
        if (dish.isVegetarian == true) {
            isVegeterianCB.isChecked = true
        }
        if (dish.isEggFree == true) {
            isEggFreeCB.isChecked = true
        }
        if (dish.isSoyfree == true) {
            isSoyFreeCB.isChecked = true
        }
        if (dish.isFreeFromSeeFood == true) {
            isSeaFoodFreeCB.isChecked = true
        }
        if (dish.isLCHF == true) {
            isLCHFCB.isChecked = true
        }
        if (dish.canBeMadeVegan == true) {
            canBeMadeVeganCB.isChecked = true
        }
        if (dish.canBeMadeVegeterian == true) {
            canBeMadeVegeterianCB.isChecked = true
        }
        if (dish.canBeMadeLaktosFree == true) {
            canBeMadeLaktoseFreeCB.isChecked =true
        }
        if (dish.canBeMadeGlutenFree == true) {
            canBeMadeGlutenFreeCB.isChecked = true
        }

        if (dish.extraCostLaktose != null) {
            laktosExtraCostET.setText(dish.extraCostLaktose.toString())
        }
        if (dish.extraCostGluten != null) {
            glutenExtraCostET.setText(dish.extraCostGluten.toString())
        }
        if (dish.extraCostVegan != null) {
            laktosExtraCostET.setText(dish.extraCostVegan.toString())
        }
        if (dish.extraCostVegeterian != null) {
            vegeterianExtraCostET.setText(dish.extraCostVegeterian.toString())
        }
        if (dish.priceSmallPortion!= null) {
            smalPriceET.setText(dish.priceSmallPortion.toString())
        }
        if (dish.priceNormalPortion!= null) {
            normalPriceET.setText(dish.priceNormalPortion.toString())
        }
        if (dish.priceLargePortion != null) {
            largePriceET.setText(dish.priceLargePortion.toString())
        }


    }
// TODO - edit through firebase - only possible local
    fun EditDish(position: Int){

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

        if (veganExtraCostET.text.toString().isNotEmpty()) {
            DataManagerDishes.dishes[position].extraCostVegan =veganExtraCostET.text.toString().toDouble()
        }

        if (vegeterianExtraCostET.text.isNotEmpty() ) {
            DataManagerDishes.dishes[position].extraCostVegeterian = vegeterianExtraCostET.text.toString().toDouble()
        }

        if (glutenExtraCostET.text.toString().isNotEmpty()) {
            DataManagerDishes.dishes[position].extraCostVegeterian = glutenExtraCostET.text.toString().toDouble()
        }

        if (laktosExtraCostET.text.toString().isNotEmpty() ) {
            DataManagerDishes.dishes[position].extraCostLaktose =laktosExtraCostET.text.toString().toDouble()
        }

        if (smalPriceET.text.toString().isNotEmpty()) {
            DataManagerDishes.dishes[position].priceSmallPortion = smalPriceET.text.toString().toDouble()
        }

        if (largePriceET.text.toString().isNotEmpty()) {
            DataManagerDishes.dishes[position].priceLargePortion = largePriceET.text.toString().toDouble()
        }

        if (normalPriceET.text.toString().isNotEmpty() ) {
            DataManagerDishes.dishes[position].priceNormalPortion = normalPriceET.text.toString().toDouble()
        }

         db.collection("restaurants")
        .document(restaurantIdent)
        .collection("dishes")
        .document(DataManagerDishes.dishes[position].documentId!!)
        .set(DataManagerDishes.dishes[position])



        finish()


    }


    fun AddDish() {


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
        //TODO check the checkbox if price for extra glutenfree


        var canBeMadeGlutenfree = false
        if (canBeMadeGlutenFreeCB.isChecked) {
            canBeMadeGlutenfree = true
        }

        var extraCostGluten: Double? = null
        if (glutenExtraCostET.text.isNotEmpty()) {
            extraCostGluten = glutenExtraCostET.text.toString().toDouble()
        }
        var canBeMadeLaktosefree = false
        if (canBeMadeLaktoseFreeCB.isChecked) {
            canBeMadeLaktosefree = true
        }

        var extraCostLaktose: Double? = null
        if ( laktosExtraCostET.text.isNotEmpty() ) {
            extraCostLaktose = laktosExtraCostET.text.toString().toDouble()
        }
        var canBeMadeVegeterian = false
        if (canBeMadeVegeterianCB.isChecked) {
            canBeMadeVegeterian = true
        }

        var extraCostVegeterian: Double? = null
        if ( vegeterianExtraCostET.text.isNotEmpty()) {
            extraCostVegeterian = vegeterianExtraCostET.text.toString().toDouble()
        }

        var canBeMadeVegan = false
        if (canBeMadeVeganCB.isChecked) {
            canBeMadeVegan = true
        }

        var extraCostVegan: Double? =null
        if ( veganExtraCostET.text.isNotEmpty() ) {
            extraCostVegan = veganExtraCostET.text.toString().toDouble()
        }

        var priceNormalPortion: Double? = null
        if ( normalPriceET.text.isNotEmpty()) {
            priceNormalPortion = normalPriceET.text.toString().toDouble()
        }
        var priceSmalPortion: Double? = null
        if ( smalPriceET.text.isNotEmpty()) {
            priceSmalPortion = smalPriceET.text.toString().toDouble()
        }

        var priceLargePortion: Double? = null
        if (largePriceET.text.isNotEmpty()) {
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

        if (priceLargePortion == null && priceSmalPortion == null
            &&  priceNormalPortion == null)
         {
            Toast.makeText(
                applicationContext,
                "Minst 1 pris för 1 storlek måste vara angiven",
                Toast.LENGTH_LONG
            ).show()
            priceChosen = false
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
                "",
                "/dishes/default_food.jpg"
            )

           // DataManagerDishes.dishes.add(newDish) only local
            db.collection("restaurants").document(restaurantIdent).collection("dishes").add(newDish)
           //update
            val foodsRef = db.collection("restaurants").document(restaurantIdent).collection("dishes")
            foodsRef.addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    DataManagerDishes.dishes.clear()
                    for (document in snapshot.documents) {
                        if (document != null) {
                            document.toObject<Dishes>()
                                ?.let { DataManagerDishes.dishes.add(it) }
                        }
                    }
                }
            }

            finish()

        }


    }
}
