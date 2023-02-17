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

lateinit var drink: Drink

class DrinksViewRecyclerAdapter(val context: Context, val drink : List<Drink>): RecyclerView.Adapter<DrinksViewRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_drinks, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = drink[position]
        holder.titleDisplay.text = currentItem.drinkName
        holder.smallDrinkText.text = " 33cl" + currentItem.drinkSize
        holder.mediumDrinkText.text = " 50cl" + currentItem.drinkSize
        holder.largeDrinkText.text = " 1.5L" + currentItem.drinkSize
        holder.smallPriceTV.text = currentItem.drinkPrice.toString() + " kr"
        holder.mediumPriceTV.text = currentItem.drinkPrice.toString() + " kr"
        holder.largePriceTV.text = currentItem.drinkPrice.toString() + " kr"
        holder.drinkType.text = currentItem.drinkType
        holder.imageDisplay

    }

    override fun getItemCount(): Int {

        return drink.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageDisplay: ImageView = itemView.findViewById(R.id.drinkImage)
        val titleDisplay: TextView = itemView.findViewById(R.id.drinksNameTextView)
        val drinkType: TextView = itemView.findViewById(R.id.drinkTypeTextView)
        val smallDrinkText: TextView = itemView.findViewById(R.id.smallTextView)
        val mediumDrinkText: TextView = itemView.findViewById(R.id.mediumTextView)
        val largeDrinkText: TextView = itemView.findViewById(R.id.largeTextView)
        val smallPriceTV: TextView = itemView.findViewById(R.id.smallPriceTV)
        val mediumPriceTV: TextView = itemView.findViewById(R.id.mediumPriceTV)
        val largePriceTV: TextView = itemView.findViewById(R.id.largePriceTV)
        val smallPriceAdd: Button = itemView.findViewById(R.id.smallAddButton)
        val mediumPriceAdd: Button = itemView.findViewById(R.id.mediumAddButton)
        val largePriceAdd: Button = itemView.findViewById(R.id.largeAddButton)
        var drinkDisplayPosition = 0

        init {

            smallPriceAdd.setOnClickListener {
                val selectedDrink = DataManagerDrinks.drinkList[drinkDisplayPosition]
                selectedDrink.drinkSize = "33cl"
                selectedDrink.drinkName += "  33cl"
            }

            mediumPriceAdd.setOnClickListener {
                val selectedDrink = DataManagerDrinks.drinkList[drinkDisplayPosition]
                selectedDrink.drinkSize = "50cl"
                selectedDrink.drinkName += "  50cl"

                largePriceAdd.setOnClickListener {
                    val selectedDrink = DataManagerDrinks.drinkList[drinkDisplayPosition]
                    selectedDrink.drinkSize = "1.5l"
                    selectedDrink.drinkName += "  1.5l"
                }
            }
        }
    }
}






