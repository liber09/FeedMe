package com.example.feedme

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.data.Dishes

class ShoppingCartReyclerAdapter(val context: Context, val shoppingCartItems: List<Dishes>): RecyclerView.Adapter<ShoppingCartReyclerAdapter.ViewHolder>() {

    var layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item_shopping_cart, parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItems = shoppingCartItems[position]
        holder.cartItemRowTitle.text = cartItems.title
        holder.cartItemRowCount.text = cartItems.count.toString()
        if(cartItems.selectedFoodSize == "s"){
            holder.cartItemRowPrice.text = cartItems.priceSmallPortion.toString()+ " Kr"
        }
        if(cartItems.selectedFoodSize == "n"){
            holder.cartItemRowPrice.text = cartItems.priceNormalPortion.toString()+ " Kr"
        }
        if(cartItems.selectedFoodSize == "l"){
            holder.cartItemRowPrice.text = cartItems.priceLargePortion.toString()+ " Kr"
        }
        var specialsSum = 0.0
        //add extraCost for vegan
        if(cartItems.isVegan) {
            specialsSum+= cartItems.extraCostVegan!!
        }
        //add extraCost for vegetarian
        if(cartItems.isVegetarian){
            specialsSum += cartItems.extraCostVegeterian!!
        }
        //add extraCost for glutenFree
        if(cartItems.isGlutenFree) {
            specialsSum += cartItems.extraCostGluten!!
        }
        //add extraCost for lactoseFree
        if(cartItems.isLaktoseFree) {
            specialsSum += cartItems.extraCostLaktose!!
        }
        holder.cartItemRowSpecialsPrice.text = specialsSum.toString() + " Kr"
        holder.cartDisplayPosition = position
        holder.increaseButton.setOnClickListener{
            cartItems.count++
            holder.cartItemRowCount.text = cartItems.count.toString()
        }
        holder.decreaseButton.setOnClickListener{
            cartItems.count--
            holder.cartItemRowCount.text = cartItems.count.toString()
        }
    }

    override fun getItemCount(): Int {
        return shoppingCartItems.size
    }

    inner class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartItemRowTitle = itemView.findViewById<TextView>(R.id.textViewCartRowItemTitle)
        val cartItemRowPrice = itemView.findViewById<TextView>(R.id.textViewCartItemRowPrice)
        val cartItemRowCount = itemView.findViewById<TextView>(R.id.TVShoppingCartItemCount)
        val cartItemRowSpecialsPrice = itemView.findViewById<TextView>(R.id.TVShoppingCartRowItemSpeecialsAmount)
        val increaseButton = itemView.findViewById<Button>(R.id.btnShoppingCartIncrease)
        val decreaseButton = itemView.findViewById<Button>(R.id.btnShoppingCartDecrease)

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