package com.example.feedme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class FoodViewRecyclerAdapter(val context: Context, val mainCourses : List<Dishes>): RecyclerView.Adapter<FoodViewRecyclerAdapter.ViewHolder>() {

    var layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item_food, parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dishes = mainCourses[position]

        holder.nameDishTextView.text = dishes.title
        holder.descriptionDishTextView.text = dishes.description
        holder.normalPriceTextView.text = "R   "+dishes.priceNormalPortion.toString()+" kr"
        holder.smalPriceTextView.text = "S   " + dishes.priceSmallPortion.toString()+" kr"
        holder.largePriceTextView.text = "L   "+ dishes.priceLargePortion.toString()+" kr"
        holder.tv_priceLaktose.text = dishes.extraCostLaktose.toString()+" kr"
        holder.tv_priceGluten.text = dishes.extraCostGluten.toString()+" kr"
        holder.tv_priceVegan.text = dishes.extraCostVegan.toString()+" kr"
        holder.tv_priceVegetarian.text = dishes.extraCostVegeterian.toString()+ " kr"

        if (dishes.priceSmallPortion == null){
            holder.addButtonSmalPrice.isInvisible = true
            holder.smalPriceTextView.isInvisible = true
        }
        if (dishes.priceLargePortion == null){
            holder.addButtonLargePrice.isInvisible = true
            holder.largePriceTextView.isInvisible = true
        }
        if (dishes.priceNormalPortion == null){
            holder.addButtonNormalPrice.isInvisible = true
            holder.normalPriceTextView.isInvisible = true
        }
        if (dishes.canBeMadeGlutenFree == false){
            holder.checkBoxGluten.isInvisible = true
            holder.tv_priceGluten.isInvisible = true
        }
        if (dishes.canBeMadeLaktosFree== false){
            holder.checkBoxLaktose .isInvisible = true
            holder.tv_priceLaktose.isInvisible = true
        }
        if (dishes.canBeMadeVegan == false){
            holder.checkBoxVegan.isInvisible = true
            holder.tv_priceVegan.isInvisible = true
        }
        if (dishes.canBeMadeVegeterian == false){
            holder.checkBoxVegeterian.isInvisible = true
            holder.tv_priceVegetarian.isInvisible = true
        }







    }

    override fun getItemCount(): Int {
        return mainCourses.size

    }


    inner class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var nameDishTextView = itemView.findViewById<TextView>(R.id.tv_RV_DishName)
        var descriptionDishTextView = itemView.findViewById<TextView>(R.id.tv_RV_dishDescription)
        var normalPriceTextView = itemView.findViewById<TextView>(R.id.tv_RV_NormalPrice)
        var smalPriceTextView = itemView.findViewById<TextView>(R.id.tv_RV_SmalpPrice)
        var largePriceTextView = itemView.findViewById<TextView>(R.id.tv_RV_LargePrice)
        var addButtonSmalPrice = itemView.findViewById<Button>(R.id.btn_AddSmallPrice)
        var addButtonLargePrice = itemView.findViewById<Button>(R.id.btn_AddLarge)
        var addButtonNormalPrice = itemView.findViewById<Button>(R.id.btn_AddNormalPrice)
        var checkBoxGluten = itemView.findViewById<CheckBox>(R.id.cB_glutenFree)
        var checkBoxLaktose = itemView.findViewById<CheckBox>(R.id.cN_laktosFree)
        var checkBoxVegan = itemView.findViewById<CheckBox>(R.id.cB_vegan)
        var checkBoxVegeterian = itemView.findViewById<CheckBox>(R.id.cB_vegeterian)
        var tv_priceGluten = itemView.findViewById<TextView>(R.id.tv_priceGlutenFree)
        var tv_priceLaktose = itemView.findViewById<TextView>(R.id.tv_priceLaktosFree)
        var tv_priceVegan = itemView.findViewById<TextView>(R.id.tv_priceVegan)
        var tv_priceVegetarian = itemView.findViewById<TextView>(R.id.tv_RV_priceVegeterian)



    }

}