package com.example.feedme

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.feedme.data.Order
import com.example.feedme.data.OrderItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CheckoutActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var adressText:  EditText
    lateinit var cust: String
    lateinit var customerPhone: String
    lateinit var customerNumber: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        val messageToRest = intent.getStringExtra("MESSAGETOREST").toString()
        val selectedDeliveryOption = intent.getStringExtra("DELIVERYOPTION").toString()
        val totalOrderAmount = intent.getStringExtra("TOTALAMOUNT").toString()
        adressText = findViewById(R.id.editTextTextMultiLine2)
        auth = Firebase.auth
        val user = auth.currentUser
        if (user!= null){
            var count = 0
            for (customer in DataManagerCustomers.customers){
                if (user.uid == customer.customerId){
                    Log.d("!!!", customer.postalCode)
                    adressText.setText(customer.firstName+" "+
                            customer.lastName+"\n"+customer.address+"\n"
                    +customer.postalCode+" "+customer.city)
                    customerPhone = customer.phoneNumber
                    customerNumber = count.toString()
                    cust=(customer.firstName+" "+customer.lastName+" "+customer.address+" "+customer.postalCode+" "+customer.city)
                }
                count++
            }

        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val btnCompleteOrder = findViewById<Button>(R.id.btnFinishOrder)
        btnCompleteOrder.setOnClickListener{
            val arInfo: ArrayList<String> = ArrayList()
           // arInfo.add(findViewById<EditText>(R.id.editTextTextMultiLine2).text.toString())
            arInfo.add(cust)
            val payKlarna = findViewById<CheckBox>(R.id.cbKlarna).isChecked
            val paySwish = findViewById<CheckBox>(R.id.cbSwish).isChecked
            val payPayPal = findViewById<CheckBox>(R.id.cbPayPal).isChecked
            val payBankCard = findViewById<CheckBox>(R.id.cbBankCard).isChecked

            if(payKlarna){
                arInfo.add(getString(R.string.klarna))
            }
            if(paySwish){
                arInfo.add(getString(R.string.swish))
            }
            if(payPayPal){
                arInfo.add(getString(R.string.paypal))
            }
            if(payBankCard){
                arInfo.add(getString(R.string.bankCard))
            }
            val orderedDishes = mutableListOf<OrderItem>()
            for (item in DataManagerShoppingCart.shoppingCartItems){
                val orderItem = OrderItem(
                    item.title
                )
                orderedDishes.add(orderItem)
            }
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val currentTime = LocalDateTime.now().format(formatter)
            val restDocumentId = DataManagerShoppingCart.shoppingCartItems.get(0).restaurantDocumentId
            val order = Order(
                restDocumentId,
                user?.uid,
                "",
                orderedDishes,
                currentTime,
                DataManagerOrders.orders.size + 1,
                customerPhone,
                totalOrderAmount.toDouble(),
                selectedDeliveryOption,
                customerNumber.toInt(),
                messageToRest)

            order.restaurantDocumentId?.let { it1 -> db.collection("restaurants").document(it1)
                .collection("orders").add(order) }

            val intent = Intent(this, CustomerOrderConfirmationActivity::class.java)
            intent.putStringArrayListExtra("list",arInfo)
            startActivity(intent)
        }
        val chbOtherAddress = findViewById<CheckBox>(R.id.chbOtherAddress)
        val addressField = findViewById<EditText>(R.id.editTextTextMultiLine2)
        chbOtherAddress.setOnClickListener{
            addressField.isFocusableInTouchMode = chbOtherAddress.isChecked
        }



        val btnHome = findViewById<ImageView>(R.id.imgCheckoutHome)
        btnHome.setOnClickListener{
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        val btnBack = findViewById<ImageView>(R.id.imgCheckoutBack)
        btnBack.setOnClickListener{
            finish()
        }

    }
}