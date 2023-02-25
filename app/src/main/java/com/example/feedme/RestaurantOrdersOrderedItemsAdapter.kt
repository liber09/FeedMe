package com.example.feedme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.data.Order

class RestaurantOrdersOrderedItemsAdapter (
    val context: Context,
    val orders: List<Order>
) :

    RecyclerView.Adapter<RestaurantOrdersOrderedItemsAdapter.DataViewHolder>() {
    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_ordered_dishes, parent, false
            )
    )

    override fun getItemCount(): Int {
        return orders.count()
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderedDish = itemView.findViewById<TextView>(R.id.orderedDish)
    }

    override fun onBindViewHolder(holder: RestaurantOrdersOrderedItemsAdapter.DataViewHolder, position: Int) {
        val orderRow = orders[position].orderedDishes
        if (orderRow != null) {
            for (row in orderRow)
                holder.orderedDish.text = row.title
        }
    }
}