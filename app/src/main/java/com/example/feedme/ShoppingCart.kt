package com.example.feedme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.feedme.data.Cart

var shoppingCartItems: MutableList<Dishes> = arrayListOf()


class ShoppingCart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
    }

    fun addToCart(item:Dishes){
        var cartItem = item.title?.let {
            Cart(
                rowCount = 2,
                rowTitle = item.title!!,
                rowAllergen = item.isEggFree.toString()
            )
        }
    }

    fun removeFromCart(item:Dishes){
        shoppingCartItems
    }

}