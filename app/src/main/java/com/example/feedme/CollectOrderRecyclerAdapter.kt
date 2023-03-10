package com.example.feedme

import android.content.Context
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.feedme.data.Dishes
import com.example.feedme.data.Order
import com.example.feedme.data.Restaurant

//Todo byta dishes till restaurants när all data är ner

class CollectOrderRecyclerAdapter(val context: Context,
                                  val restaurants: List<Restaurant>,
                                  val listener: OnClickListener
) :
RecyclerView.Adapter<CollectOrderRecyclerAdapter.ViewHolder>(){

    val layoutInflater = LayoutInflater.from(context)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val itemView = layoutInflater.inflate(R.layout.food2collect_list_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]

        holder.tv_restaurantName.text = restaurant.name
        holder.tv_restaurantAdress.text = restaurant.address + " "+restaurant.postalCode +" "+restaurant.city


        val orderReference = db.collection("restaurants")
            .document(restaurant.documentId.toString())
            .collection("orders")
        orderReference.get().addOnSuccessListener { snapshot ->
            if (snapshot.isEmpty) {
                Log.d("lalaa", "tomt")
                return@addOnSuccessListener
            }
            val orderList = snapshot.toObjects(Order::class.java)

            // Get the customer number from each order and filter the customers list to get the relevant customer
            val customerList = DataManagerCustomers.customers.filter { customer ->
                orderList.any { order ->
                    val customerNumberString = order.customerPhoneNumber.toString()
                    customerNumberString.isNotBlank() && customerNumberString == customer.phoneNumber
                }
            }

            val customerAdapter = CustomerAdressDeliveryReceiyclerAdapter(context, customerList)
            holder.rv_forCustomerAdress.layoutManager = LinearLayoutManager(context)
            holder.rv_forCustomerAdress.adapter = customerAdapter
            holder.restaurantDisplayPosition = position

    }}

    override fun getItemCount() = restaurants.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tv_restaurantName = itemView.findViewById<TextView>(R.id.tv_RVDeliv_RestName)
        val tv_restaurantAdress = itemView.findViewById<TextView>(R.id.tv_RVDel_RestAdress)
        val rv_forCustomerAdress = itemView.findViewById<RecyclerView>(R.id.RV_customerInfo)
        val btn_gps2restaurant = itemView.findViewById<Button>(R.id.btn_GPS2RestaurantRVDeliveryGuy)
        var restaurantDisplayPosition = 0

        init {
            btn_gps2restaurant.setOnClickListener {
                val position = adapterPosition
                listener.OnClick(position)
              /*  val target = DataManagerRestaurants.restaurants[restaurantDisplayPosition]
                val intent = Intent(context,DeliveryMapsActivity::class.java)
                intent.putExtra("target","$target")
                context.startActivity(intent)*/


            }
        }




    }
    interface OnClickListener {
        fun OnClick(position: Int)


    }




}