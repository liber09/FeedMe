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

lateinit var drink: Drink

class DrinksViewRecyclerAdapter(val context: Context, val drink : List<Drink>): RecyclerView.Adapter<DrinksViewRecyclerAdapter.ViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_drinks, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int) {   //Visar position för drink i listan, kopplar samman varje current item(drink) med variabler från dataclass
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
                val imageURL = Uri.toString() // get the URL for the image
                //Use third party product glide to load the image into the imageview
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

        val imageDisplay: ImageView =
            itemView.findViewById(R.id.drinkImage)       // Visar vilka variabler som är kopplat till viewholder
        val titleDisplay: TextView = itemView.findViewById(R.id.drinksNameTextView)
        val drinkType: TextView = itemView.findViewById(R.id.drinkTypeTextView)
        val drinkSize: TextView = itemView.findViewById(R.id.sizeTextView)
        val priceTV: TextView = itemView.findViewById(R.id.priceTV)
        val addToCart: Button = itemView.findViewById(R.id.cartAddButton)
        var drinkDisplayPosition = 0


        init {                                  // add to cart function
            addToCart.setOnClickListener {
                val selectedDrink = DataManagerDrinks.drinkList[drinkDisplayPosition]
                selectedDrink.drinkSize = "33cl"
                selectedDrink.drinkName += "  33cl"
                handleExistsInCart(selectedDrink)

            }
        }

        fun handleExistsInCart(selectedDrink: Drink) { // Funktion som hanterar dubletter i varukorg
            var alreadyInCart = false
            for (Drink in DataManagerShoppingCart.shoppingCartItems) {
                if (selectedDrink.equals(Drink)) {
                    Drink.count++
                    alreadyInCart = true

                }

                if (!alreadyInCart) {
                    selectedDrink.count++
                    DataManagerShoppingCart.shoppingCartItems.add(Drink)

                }
            }
        }
    }
}






