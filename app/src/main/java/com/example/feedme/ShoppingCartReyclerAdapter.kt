package com.example.feedme

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.data.Dishes
import com.google.android.material.chip.Chip

class ShoppingCartReyclerAdapter(val context: Context, val shoppingCartItems: List<Dishes>): RecyclerView.Adapter<ShoppingCartReyclerAdapter.ViewHolder>() {

    var layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item_shopping_cart, parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItems = shoppingCartItems[position]
        holder.cartItemRowTitle.text = cartItems.title
        if(cartItems.selectedFoodSize == "s"){
            holder.cartItemRowPrice.text = cartItems.priceSmallPortion.toString()+ " Kr"
        }
        if(cartItems.selectedFoodSize == "n"){
            holder.cartItemRowPrice.text = cartItems.priceNormalPortion.toString()+ " Kr"
        }
        if(cartItems.selectedFoodSize == "l"){
            holder.cartItemRowPrice.text = cartItems.priceLargePortion.toString()+ " Kr"
        }
        holder.cartDisplayPosition = position
    }

    override fun getItemCount(): Int {
        return shoppingCartItems.size
    }

    inner class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartItemRowTitle = itemView.findViewById<TextView>(R.id.textViewCartRowItemTitle)
        val cartItemRowPrice = itemView.findViewById<TextView>(R.id.textViewCartItemRowPrice)

        var cartDisplayPosition = 0


        init {
            itemView.setOnClickListener(){
                val intent = Intent(context,AddNChangeFoodActivity::class.java)
                intent.putExtra(DISH_POSTION_KEY, cartDisplayPosition)
                context.startActivity(intent)
            }
        }
    }
}