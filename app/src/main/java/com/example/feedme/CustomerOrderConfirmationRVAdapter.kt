package com.example.feedme

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.DataManagerShoppingCart.shoppingCartItems
import com.example.feedme.data.Dishes

class CustomerOrderConfirmationRVAdapter(val context: Context, val orderConfirmationItems: List<Dishes>): RecyclerView.Adapter<CustomerOrderConfirmationRVAdapter.ViewHolder>() {
    var layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerOrderConfirmationRVAdapter.ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item_order_confirmation, parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomerOrderConfirmationRVAdapter.ViewHolder, position: Int) {
        val OrderConfirmationItem = shoppingCartItems[position]
        holder.orderConfirmationRowItemName.text = OrderConfirmationItem.title
        if(OrderConfirmationItem.selectedFoodSize == "s"){
            holder.orderConfirmationRowItemPrice.text = OrderConfirmationItem.priceSmallPortion.toString()+ " Kr"
        }
        if(OrderConfirmationItem.selectedFoodSize == "n"){
            holder.orderConfirmationRowItemPrice.text = OrderConfirmationItem.priceNormalPortion.toString()+ " Kr"
        }
        if(OrderConfirmationItem.selectedFoodSize == "l"){
            holder.orderConfirmationRowItemPrice.text = OrderConfirmationItem.priceLargePortion.toString()+ " Kr"
        }
    }

    override fun getItemCount(): Int {
        return DataManagerShoppingCart.shoppingCartItems.size
    }

    inner class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderConfirmationRowItemName = itemView.findViewById<TextView>(R.id.TVOrderConfirmationDishTitle)
        val orderConfirmationRowItemPrice = itemView.findViewById<TextView>(R.id.TVOrderConfirmationDishPrice)
    }
}