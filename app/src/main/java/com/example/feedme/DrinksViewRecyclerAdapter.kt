package com.example.feedme

import Drink
import android.content.Context
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
import com.nex3z.notificationbadge.NotificationBadge

lateinit var drink: Drink

class DrinksViewRecyclerAdapter(val context: Context, val drink : List<Drink>):                       // abstrakt klass som grund för att binda data till recycleview
    RecyclerView.Adapter<DrinksViewRecyclerAdapter.ViewHolder>()
{



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_drinks, parent, false)
        return ViewHolder(itemView)  // För att få in varje item i listan i en viewholder som dyker upp i recycleview
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {   //Visar position för drink i listan, kopplar samman varje current item(drink) med variabler från dataclass
        val currentItem = drink[position]
        holder.titleDisplay.text = currentItem.drinkName
        holder.drinkSize.text = " " + currentItem.drinkSize
        holder.priceTV.text = currentItem.drinkPrice.toString() + " kr"
        holder.drinkType.text = currentItem.drinkType
        holder.imageDisplay




       // Glide implemented for images to show im menu
        if (currentItem.imagePath.isEmpty()){
            holder.imageDisplay.setImageResource(R.drawable.drinkslogo)
        }else{
            val imageref = Firebase.storage.reference.child(currentItem.imagePath)
            imageref.downloadUrl.addOnSuccessListener { Uri ->
                val imageURL = Uri.toString()
                Glide.with(context)
                    .load(imageURL)
                    .into( holder.imageDisplay)
            }
        }
    }



    override fun getItemCount(): Int {

        return drink.size

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageDisplay: ImageView = itemView.findViewById(R.id.drinkImage)       // Visar vilka variabler som är kopplat till viewholder
        val titleDisplay: TextView = itemView.findViewById(R.id.drinksNameTextView)
        val drinkType: TextView = itemView.findViewById(R.id.drinkTypeTextView)
        val drinkSize: TextView = itemView.findViewById(R.id.sizeTextView)
        val priceTV: TextView = itemView.findViewById(R.id.priceTV)

      }
}




























