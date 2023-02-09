package com.example.feedme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.data.Dishes



lateinit var recyclerViewShoppingCart : RecyclerView
class ShoppingCart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

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
        var sum:Double = 0.0
        var restaurantId = ""
        for (item in DataManagerShoppingCart.shoppingCartItems) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            //if title prefixed "s " customer has chosen small portion
            if (item.selectedFoodSize == "s") {
                sum += item.priceSmallPortion!!
                //if title prefixed "n " customer has chosen normal portion
            } else if (item.selectedFoodSize == "n") {
                sum += item.priceNormalPortion!!
                //if title prefixed "l " customer has chosen large portion
            } else if (item.selectedFoodSize == "l") {
                sum += item.priceLargePortion!!
            }
            //add extraCost for vegan
            if(item.isVegan) {
                sum+= item.extraCostVegan!!
            }
            //add extraCost for vegetarian
            if(item.isVegetarian){
                sum += item.extraCostVegeterian!!
            }
            //add extraCost for glutenFree
            if(item.isGlutenFree) {
                sum += item.extraCostGluten!!
            }
            //add extraCost for lactoseFree
            if(item.isLaktoseFree) {
                sum += item.extraCostLaktose!!
            }
            restaurantId = item.restaurantDocumentId.toString()
        }
        val deliveryPrice = DataManagerRestaurants.getByDocumentId(restaurantId)?.deliveryFee
        //Print new shoppingCart total to screen

        if (deliveryPrice != null) {
            sum += deliveryPrice
        }
        val deliveryFee = findViewById<TextView>(R.id.TVShoppingCartDeliveryFee)
        val totalPriceView = findViewById<TextView>(R.id.TVShoppingCartTotal)
        totalPriceView.text = sum.toString()+ " Kr"
        deliveryFee.text = deliveryPrice.toString() + " Kr"
    }
}