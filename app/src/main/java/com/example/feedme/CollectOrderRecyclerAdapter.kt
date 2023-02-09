package com.example.feedme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.feedme.data.Restaurant

//Todo byta dishes till restaurants när all data är ner

class CollectOrderRecyclerAdapter(val context: Context,
                                  val dishes: List<Dishes>) :
RecyclerView.Adapter<CollectOrderRecyclerAdapter.ViewHolder>(){

    val layoutInflater = LayoutInflater.from(context)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val itemView = layoutInflater.inflate(R.layout.food2collect_list_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = dishes[position]

        holder.tv_restaurantName.text = dish.title
        holder.tv_restaurantAdress.text = dish.category
        val customerAdapter = CustomerAdressDeliveryReceiyclerAdapter(context, DataManagerDishes.dishes)
        holder.rv_forCustomerAdress.layoutManager = LinearLayoutManager(context)
        holder.rv_forCustomerAdress.adapter = customerAdapter

    }

    override fun getItemCount() = dishes.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tv_restaurantName = itemView.findViewById<TextView>(R.id.tv_RVDeliv_RestName)
        val tv_restaurantAdress = itemView.findViewById<TextView>(R.id.tv_RVDel_RestAdress)
        val rv_forCustomerAdress = itemView.findViewById<RecyclerView>(R.id.RV_customerInfo)



    }


}