package com.example.feedme

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.feedme.data.OrderItem
import com.google.firebase.firestore.ktx.toObject

class CheatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)



        val sc = findViewById<Button>(R.id.btn_Shopping)

        val ra = findViewById<Button>(R.id.buttonRegister)


        val ci = findViewById<Button>(R.id.btn_customerInfo)
        val rov = findViewById<Button>(R.id.btnOrderView)
        val getOrders = findViewById<Button>(R.id.btnGetOrders)

        val menu = findViewById<Button>(R.id.menuBtn)



        val tvresId = findViewById<EditText>(R.id.resNrEdtTxt )
        val restv = findViewById<TextView>(R.id.btnRestaurantList)


        restv.setOnClickListener{
            val intent= Intent(this,RestaurantViewActiviity::class.java)
            startActivity(intent)
        }

        menu.setOnClickListener{
            val intent= Intent(this,MenuActivity::class.java)
            startActivity(intent)

        }



        getOrders.setOnClickListener{
            val restaurantId = tvresId.text.toString()
            getOrdersForRestaurant(restaurantId)
        }

        rov.setOnClickListener{
            val intent= Intent(this,OrderViewForRestaurants::class.java)
            startActivity(intent)
        }
        ci.setOnClickListener{
            val intent= Intent(this,RegisterCustomerInfo::class.java)
            startActivity(intent)
        }

        sc.setOnClickListener{
            val intent= Intent(this,ShoppingCart::class.java)
            startActivity(intent)
        }


        /*rr.setOnClickListener {    val intent= Intent(this,InfoRestaurantActivity::class.java)
            intent.putExtra("RESTAURANT_KEY",1)
            startActivity(intent) }*/



        ra.setOnClickListener{
            val intent= Intent(this,LoginAndRegisterActivity::class.java)
            startActivity(intent)
        }







    }
    fun getOrdersForRestaurant(restaurantId: String){
        DataManagerOrders.orders.clear()
        var index = 0
        db.collection("restaurants").document(restaurantId).collection("orders")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    DataManagerOrders.orders.add(document.toObject())
                    db.collection("restaurants").document(restaurantId).collection("orders").document(document.id).collection("orderedDishes")
                        .get()
                        .addOnSuccessListener { orderItems ->
                            for(orderItem in orderItems) {
                                DataManagerOrders.orders.get(index).orderedDishes.add(orderItem.toObject<OrderItem>())
                                Log.d(ContentValues.TAG, "${orderItem.id} => ${orderItem.data}")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                        }
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

}