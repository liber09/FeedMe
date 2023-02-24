package com.example.feedme

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.feedme.data.Customer
import com.example.feedme.data.Dishes

class CustomerAdressDeliveryReceiyclerAdapter (val context: Context,
val customers : List<Customer>) :
RecyclerView.Adapter<CustomerAdressDeliveryReceiyclerAdapter.ViewHolder>(){

    val layoutInflater = LayoutInflater.from(context)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item_customer_info_collcet,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customers = customers[position]

        holder.tv_customerName.text = customers.firstName + " "+customers.lastName
        holder.tv_customerAdress.text = customers.address
        holder.tv_customarZipNCity.text = customers.postalCode + " "+ customers.city
        holder.tv_customerTelefone.text = customers.phoneNumber
        holder.customerDisplayPosition = position





    }

    override fun getItemCount() = customers.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tv_customerName = itemView.findViewById<TextView>(R.id.tv_RV4Delv_Customer_name)
        val tv_customerAdress = itemView.findViewById<TextView>(R.id.tv_RVDelv_CustomerAdress)
        val btn_gps4kund = itemView.findViewById<Button>(R.id.btn_GPS4Kund)
        val tv_customarZipNCity = itemView.findViewById<TextView>(R.id.tv_customerPostCodeCity)
        val tv_customerTelefone = itemView.findViewById<TextView>(R.id.tv_customerTelefone)
        var customerDisplayPosition = 0

        init {
            btn_gps4kund.setOnClickListener {
                val target = DataManagerCustomers.customers[customerDisplayPosition]
                val intent = Intent(context, DeliveryMapsActivity::class.java)
                intent.putExtra("target", "$target")
                context.startActivity(intent)


            }

        }
    }


}