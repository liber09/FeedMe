package com.example.feedme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.data.Dishes

class CustomerAdressDeliveryReceiyclerAdapter (val context: Context,
val dishes: List<Dishes>) :
RecyclerView.Adapter<CustomerAdressDeliveryReceiyclerAdapter.ViewHolder>(){

    val layoutInflater = LayoutInflater.from(context)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item_customer_info_collcet,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = dishes[position]

        holder.tv_customerName.text = dish.title
        holder.tv_customerAdress.text = dish.category



    }

    override fun getItemCount() = dishes.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tv_customerName = itemView.findViewById<TextView>(R.id.tv_RV4Delv_Customer_name)
        val tv_customerAdress = itemView.findViewById<TextView>(R.id.tv_RVDelv_CustomerAdress)



    }


}