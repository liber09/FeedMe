package com.example.feedme

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.data.Dishes
import com.example.feedme.data.Order
import org.w3c.dom.Text

class RestaurantOrderDetailsRVAdapter(
    val context: Context,
    val OrderRows: List<Dishes>
) :

    RecyclerView.Adapter<RestaurantOrderDetailsRVAdapter.ViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantOrderDetailsRVAdapter.ViewHolder {
        val itemView = layoutInflater
            .inflate(R.layout.list_item_ordered_dishes, parent, false)
        return ViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return OrderRows.count()
    }

    override fun onBindViewHolder(holder: RestaurantOrderDetailsRVAdapter.ViewHolder, position: Int) {
        val orderRow = OrderRows[position]
    }

    //Bind components
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val TVORderDetailsDishName = itemView.findViewById<TextView>(R.id.TVORderDetailsDishName)
        //val IVOrderDatailWOrWarning = itemView.findViewById<ImageView>(R.id.IVrderDatailWOrWarning)
        val imgOrderViewRowDone = itemView.findViewById<ImageView>(R.id.imgOrderViewRowDone)
        val imgOrderViewDeleteRow = itemView.findViewById<ImageView>(R.id.imgOrderViewDeleteRow)
    }}