package com.example.feedme

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.feedme.data.Dishes
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

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
            if (cartItems.extraCostVegan != null)
            {  specialsSum+= cartItems.extraCostVegan!!}
        }
        //add extraCost for vegetarian
        if(cartItems.isVegetarian){
            if (cartItems.extraCostVegeterian != null){
            specialsSum += cartItems.extraCostVegeterian!!}
        }
        //add extraCost for glutenFree
        if(cartItems.isGlutenFree) {
            if (cartItems.extraCostGluten != null){
            specialsSum += cartItems.extraCostGluten!!
        }}
        //add extraCost for lactoseFree
        if(cartItems.isLaktoseFree) {
            if (cartItems.extraCostLaktose != null){
            specialsSum += cartItems.extraCostLaktose!!
        }}
        holder.cartItemRowSpecialsPrice.text = specialsSum.toString() + " Kr"
        holder.cartDisplayPosition = position
        holder.increaseButton.setOnClickListener{
            cartItems.count++
            holder.cartItemRowCount.text = cartItems.count.toString()
            var priceToAdd = 0.0
            if(cartItems.selectedFoodSize == "s"){
                priceToAdd = cartItems.priceSmallPortion!!
            }
            if(cartItems.selectedFoodSize == "n"){
                priceToAdd = cartItems.priceNormalPortion!!
            }
            if(cartItems.selectedFoodSize == "l"){
                priceToAdd = cartItems.priceLargePortion!!
            }
            holder.cartItemRowPrice.text = (cartItems.count * priceToAdd).toString()+ " kr"
        }
        holder.decreaseButton.setOnClickListener{
            cartItems.count--
            holder.cartItemRowCount.text = cartItems.count.toString()
            var priceToAdd = 0.0
            if(cartItems.selectedFoodSize == "s"){
                priceToAdd = cartItems.priceSmallPortion!!
            }
            if(cartItems.selectedFoodSize == "n"){
                priceToAdd = cartItems.priceNormalPortion!!
            }
            if(cartItems.selectedFoodSize == "l"){
                priceToAdd = cartItems.priceLargePortion!!
            }
            holder.cartItemRowPrice.text = (cartItems.count * priceToAdd).toString()+ " kr"
        }

        if(cartItems.dishImagePath.isNotEmpty()){
            val imageref = Firebase.storage.reference.child(cartItems.dishImagePath)
            imageref.downloadUrl.addOnSuccessListener {Uri->
                val imageURL = Uri.toString()
                Glide.with(context)
                    .load(imageURL)
                    .into(holder.imgShoppingCart)
            }
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
        val imgShoppingCart = itemView.findViewById<ImageView>(R.id.imgShoppingCart)


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