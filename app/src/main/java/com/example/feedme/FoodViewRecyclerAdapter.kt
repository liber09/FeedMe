package com.example.feedme

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.feedme.data.Dishes

lateinit var dishes: Dishes

class FoodViewRecyclerAdapter(val context: Context, val mainCourses : List<Dishes>)
    : RecyclerView.Adapter<FoodViewRecyclerAdapter.ViewHolder>() {

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

        if (dishes.priceSmallPortion == null|| dishes.priceSmallPortion == 0.0){
            holder.addButtonSmallPrice.isInvisible = true
            holder.smalPriceTextView.isInvisible = true
        }
        if (dishes.priceLargePortion == null|| dishes.priceLargePortion == 0.0){
            holder.addButtonLargePrice.isInvisible = true
            holder.largePriceTextView.isInvisible = true
        }
        if (dishes.priceNormalPortion == null|| dishes.priceNormalPortion == 0.0){
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

        if (dishes.dishImagePath.isEmpty()){
            holder.iv_foodImage.setImageResource(R.drawable.logo)

        }

        holder.foodDisplayPosition = position
        //TODO change this as soon as it is klickable from a restaurant
        holder.delete_btn.setOnClickListener {
            dishes.documentId?.let { it1 ->
                db.collection("restaurants")
                    .document("restaurant2")
                    .collection("dishes")
                    .document(it1).delete()
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot sucessfully deleted!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

            }


        }


        // TODO for order food holder.checkBoxVegeterian.isChecked = food needs to be vegetrain





        //!holder.isRecyclable
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
        var addButtonSmallPrice = itemView.findViewById<Button>(R.id.btn_AddSmallPrice)
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
        var iv_foodImage = itemView.findViewById<ImageView>(R.id.iV_foodDisplay_RV)
        var foodDisplayPosition = 0
        var delete_btn = itemView.findViewById<ImageButton>(R.id.btn_delete_RV_food)


        //TODO init block för Addfunktions, and an only admin delete and change funktion

  init {

    itemView.setOnClickListener(){
    val intent = Intent(context,AddNChangeFoodActivity::class.java)
    intent.putExtra(DISH_POSTION_KEY, foodDisplayPosition)
    context.startActivity(intent)
     }


      //Add dish with size small to cart
      addButtonSmallPrice.setOnClickListener{
          val selectedDish = DataManagerDishes.dishes[foodDisplayPosition]
          selectedDish.selectedFoodSize = "s"
          selectedDish.title += "  S"
          handleExistsInCart(selectedDish)
      }

      //Add dish with size normal to cart
      addButtonNormalPrice.setOnClickListener{
          val selectedDish = DataManagerDishes.dishes[foodDisplayPosition]
          selectedDish.selectedFoodSize = "n"
          selectedDish.title += "  N"
          handleExistsInCart(selectedDish)
      }

      //Add dish with size large to cart
      addButtonLargePrice.setOnClickListener{
          val selectedDish = DataManagerDishes.dishes[foodDisplayPosition]
          selectedDish.selectedFoodSize = "l"
          selectedDish.title += "  L"
          handleExistsInCart(selectedDish)
      }

  }
    /*
    //
    // addsmall, addlarge, adddeletebutton.onClicklisterna {
    // dataManger.Dishes[foodDisplayPosition]. Video RV2   18:00

   }

    deleteButton.setOnClickListener {}
     } */

        fun handleExistsInCart(selectedDish: Dishes) {
            var alreadyInCart = false
            for (dish in DataManagerShoppingCart.shoppingCartItems) {
                if (selectedDish.equals(dish)) {
                    dish.count++
                    alreadyInCart = true
                }
            }
            if (!alreadyInCart) {
                selectedDish.count++
                DataManagerShoppingCart.shoppingCartItems.add(selectedDish)
            }
        }
    }
}