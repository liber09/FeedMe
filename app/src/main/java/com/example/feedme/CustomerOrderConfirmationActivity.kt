package com.example.feedme

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

lateinit var RVCustomerOrderConfirmation : RecyclerView
class CustomerOrderConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_order_confirmation)
        RVCustomerOrderConfirmation = findViewById<RecyclerView>(R.id.RVCustomerOrderConfirmation)
        RVCustomerOrderConfirmation.layoutManager= LinearLayoutManager(this)
        val adapter = CustomerOrderConfirmationRVAdapter(this,DataManagerShoppingCart.shoppingCartItems)
        RVCustomerOrderConfirmation.adapter = adapter
        val ar1 = intent.extras!!.getStringArrayList("list")
        val customerData = ar1?.get(0)?.split(" ")?.toMutableList()
        customerData?.removeAll(listOf("",null))

        val sb = StringBuilder()
        sb.append(customerData?.get(0).toString()).append(" ").append(customerData?.get(1).toString())
        val nameView = findViewById<TextView>(R.id.TVOrderConfirmationCustomerName)
        nameView.text = sb.toString()
        val addressView = findViewById<TextView>(R.id.TVOrderConfirmationCustomerAddress)
        sb.clear()
        sb.append(customerData?.get(2).toString()).append(" ").append(customerData?.get(3).toString())
        if (customerData != null) {
            addressView.text = sb.toString()
        }
        val postalCodeView = findViewById<TextView>(R.id.TVOrderConfirmationCustomerPostalCode)
        if (customerData != null) {
            postalCodeView.text = customerData.get(4).toString()
        }
        val cityView = findViewById<TextView>(R.id.TVOrderConfirmationCustomerCity)
        if (customerData != null) {
            cityView.text = customerData.get(5).toString()
        }
        val orderDateTime = findViewById<TextView>(R.id.TVOrderConfirmationCustomerOrderDate)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val current = LocalDateTime.now().format(formatter)
        orderDateTime.text = current.toString()
        val paymentMethod = findViewById<TextView>(R.id.TVOrderConfirmationPaymentMethod)
        sb.clear()
        sb.append(getString(R.string.paidWith)).append(ar1?.get(1).toString())
        paymentMethod.text = sb.toString()
        val EstimatedDelivery = findViewById<TextView>(R.id.TVOrderConfirmationCustomerPreliminaryArrival)
        val oneHourLater = LocalDateTime.now().plusHours(1).format(formatter)
        sb.clear()
        sb.append(getString(R.string.estimatedDelivery)).append(oneHourLater)
        EstimatedDelivery.text = sb.toString()
        calculateShoppingCart()
    }

    private fun calculateShoppingCart() {
        var sum = 0.0
        var specialsSum = 0.0
        var restaurantId = ""
        for (item in DataManagerShoppingCart.shoppingCartItems) {
            //if title prefixed "s " customer has chosen small portion
            if (item.selectedFoodSize == "s") {
                sum += (item.priceSmallPortion!!)*item.count
                //if selectedFoodSize = "s" customer has chosen normal portion
            } else if (item.selectedFoodSize == "n") {
                sum += (item.priceNormalPortion!!)*item.count
                //if selectedFoodSize = "l" customer has chosen large portion
            } else if (item.selectedFoodSize == "l") {
                sum += (item.priceLargePortion!!)*item.count
            }
            //add extraCost for vegan
            if(item.isVegan) {
                specialsSum+= (item.extraCostVegan!!)*item.count
            }
            //add extraCost for vegetarian
            if(item.isVegetarian){
                specialsSum += (item.extraCostVegeterian!!)*item.count
            }
            //add extraCost for glutenFree
            if(item.isGlutenFree) {
                specialsSum += (item.extraCostGluten!!)
            }
            //add extraCost for lactoseFree
            if(item.isLaktoseFree) {
                specialsSum += (item.extraCostLaktose!!)
            }
            restaurantId = item.restaurantDocumentId.toString()
        }
        val deliveryPrice = DataManagerRestaurants.getByDocumentId(restaurantId)?.deliveryFee
        sum += specialsSum
        val dishesTotal = findViewById<TextView>(R.id.TVOrderConfirmationCustomerOrderTotalValue)
        dishesTotal.text = sum.toString()
        if (deliveryPrice != null) {
            sum += deliveryPrice
        }
        val deliveryFee = findViewById<TextView>(R.id.TVOrderConfirmationCustomerDeliveryPrice)
        val totalPriceView = findViewById<TextView>(R.id.TVOrderConfirmationCustomerTotalCost)

        totalPriceView.text = sum.toString()+ " Kr"
        deliveryFee.text = deliveryPrice.toString() + " Kr"
    }
}