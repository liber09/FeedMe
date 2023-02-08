package com.example.feedme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

var shoppingCartItems: MutableList<Dishes> = arrayListOf()

lateinit var recyclerViewShoppingCart : RecyclerView
class ShoppingCart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

        recyclerViewShoppingCart = findViewById<RecyclerView>(R.id.rvShoppingCart)
        recyclerViewShoppingCart.layoutManager= LinearLayoutManager(this)
        val adapter = ShoppingCartReyclerAdapter(this,shoppingCartItems)
        recyclerViewShoppingCart.adapter = adapter
    }




    //Function to add dish to cart
    fun addToCart(item:Dishes){
        shoppingCartItems.add(item)
        //Calculate new cart total
        //calculateShoppingCartTotal()
    }

    //Function to remove dish from cart
    fun removeFromCart(item:Dishes){
        shoppingCartItems.remove(item)
        //Calculate new cart total
        calculateShoppingCartTotal()
    }

    //Calculates shoppingCart total.
    fun calculateShoppingCartTotal(){
        var sum:Double = 0.0
        for (item in shoppingCartItems) {
            //if title prefixed "s " customer has chosen small portion
            if (item.title?.startsWith("s ") == true) {
                sum += item.priceSmallPortion!!
                //if title prefixed "n " customer has chosen normal portion
            } else if (item.title?.startsWith("n ") == true) {
                sum += item.priceNormalPortion!!
                //if title prefixed "l " customer has chosen large portion
            } else if (item.title?.startsWith("l ") == true) {
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
        }
        //Print new shoppingCart total to screen
        val deliveryPrice = findViewById<TextView>(R.id.textViewShoppingCartDeliveryPrice).text.toString()
        val numericDeliveryPrice = stripAllButNumbers(deliveryPrice).toDouble()
        sum += numericDeliveryPrice
        findViewById<TextView>(R.id.textViewTotalShoppingCartAmount).text = sum.toString()+ " Kr"
    }

    //Function that removes all but numbers from a string and return the numeric only string
    fun stripAllButNumbers(stringToStrip: String): String {
        val reg = Regex("[^0-9 ]")
        return reg.replace(stringToStrip, "")
    }
}