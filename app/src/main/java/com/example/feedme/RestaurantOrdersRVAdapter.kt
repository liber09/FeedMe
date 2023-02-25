package com.example.feedme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.example.feedme.data.Order
import com.example.feedme.data.Restaurant

lateinit var recyclerViewRestaurantOrderRows: RecyclerView
class RestaurantOrdersRVAdapter(
    val context: Context,
    val orders: List<Order>
    ) :

    RecyclerView.Adapter<RestaurantOrdersRVAdapter.ViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantOrdersRVAdapter.ViewHolder {
        val itemView = layoutInflater
            .inflate(R.layout.list_item_restaurant_order_view, parent, false)

        recyclerViewRestaurantOrderRows = itemView.findViewById<RecyclerView>(R.id.RVOrderedItems)
        recyclerViewRestaurantOrderRows.layoutManager = LinearLayoutManager(this.context)
        val rowAdapter = RestaurantOrdersOrderedItemsAdapter(this.context,DataManagerOrders.orders)
        recyclerViewRestaurantOrderRows.adapter = rowAdapter
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return orders.count()
    }

    override fun onBindViewHolder(holder: RestaurantOrdersRVAdapter.ViewHolder, position: Int) {
        val order = orders[position]
        holder.TVOrderCustomerNumber.text = order.customerNumber.toString()
        holder.TVOrderCustomerPhone.text = order.customerPhoneNumber
        holder.tvOrderTime.text = order.orderDate
        if (order.messageFromCustomer.isNullOrEmpty()){
            holder.ivOrderWarning.isVisible = false
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOrderTime = itemView.findViewById<TextView>(R.id.TVResOrderTime)
        val btnReadyForPickup = itemView.findViewById<TextView>(R.id.btnReadyForPickup)
        val btnSendMessage = itemView.findViewById<Button>(R.id.btnSendMessage)
        val btnDelayed = itemView.findViewById<Button>(R.id.btnDelayed)
        val btnSeeMore = itemView.findViewById<Button>(R.id.btnSeeMore)
        val ivOrderWarning = itemView.findViewById<ImageView>(R.id.IVOrderWarning)
        val TVOrderCustomerNumber = itemView.findViewById<TextView>(R.id.TVOrderCustomerNumber)
        val TVOrderCustomerPhone = itemView.findViewById<TextView>(R.id.TVOrderCustomerPhone)

    }}