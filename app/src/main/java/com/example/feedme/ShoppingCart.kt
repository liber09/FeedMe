package com.example.feedme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


lateinit var recyclerViewShoppingCart : RecyclerView
class ShoppingCart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

        val btnShoppingCartCheckout = findViewById<Button>(R.id.btnShoppingCartCheckout)
        btnShoppingCartCheckout.setOnClickListener{
            val intent = Intent(this,CheckoutActivity::class.java)
            startActivity(intent)
        }
        recyclerViewShoppingCart = findViewById<RecyclerView>(R.id.rvShoppingCart)
        recyclerViewShoppingCart.layoutManager= LinearLayoutManager(this)
        val adapter = ShoppingCartReyclerAdapter(this,DataManagerShoppingCart.shoppingCartItems)
        recyclerViewShoppingCart.adapter = adapter
        calculateShoppingCartTotal()
    }

    override fun onResume() {
        super.onResume()
        recyclerViewShoppingCart.adapter?.notifyDataSetChanged()
    }

    //Calculates shoppingCart total.
    fun calculateShoppingCartTotal(){
        var sum = 0.0
        var specialsSum = 0.0
        var restaurantId = ""
        for (item in DataManagerShoppingCart.shoppingCartItems) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            //if title prefixed "s " customer has chosen small portion
            if (item.selectedFoodSize == "s") {
                sum += (item.priceSmallPortion!!)*item.count
                //if selectedFoodSize = "s" customer has chosen normal portion
            } else if (item.selectedFoodSize == "n") {
                sum += (item.priceNormalPortion!!)*item.count
                //if selectedFoodSize = "l" customer has chosen large portion
            } else if (item.selectedFoodSize == "l") {
                sum += (item.priceLargePortion!!)*item.count
            }
            //add extraCost for vegan
            if(item.isVegan) {
                specialsSum+= (item.extraCostVegan!!)*item.count
            }
            //add extraCost for vegetarian
            if(item.isVegetarian){
                specialsSum += (item.extraCostVegeterian!!)*item.count
            }
            //add extraCost for glutenFree
            if(item.isGlutenFree) {
                specialsSum += (item.extraCostGluten!!)
            }
            //add extraCost for lactoseFree
            if(item.isLaktoseFree) {
                specialsSum += (item.extraCostLaktose!!)
            }
            restaurantId = item.restaurantDocumentId.toString()
        }
        val deliveryPrice = DataManagerRestaurants.getByDocumentId(restaurantId)?.deliveryFee
        sum += specialsSum


        if (deliveryPrice != null) {
            sum += deliveryPrice
        }
        val deliveryFee = findViewById<TextView>(R.id.TVShoppingCartDeliveryFee)
        val totalPriceView = findViewById<TextView>(R.id.TVShoppingCartTotal)
        totalPriceView.text = sum.toString()+ " Kr"
        deliveryFee.text = deliveryPrice.toString() + " Kr"
    }
}