package com.example.feedme

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val btnCompleteOrder = findViewById<Button>(R.id.btnFinishOrder)
        btnCompleteOrder.setOnClickListener{
            val arInfo: ArrayList<String> = ArrayList()
            arInfo.add(findViewById<EditText>(R.id.editTextTextMultiLine2).text.toString())
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
            val intent = Intent(this, CustomerOrderConfirmationActivity::class.java)
            intent.putStringArrayListExtra("list",arInfo)
            startActivity(intent)
        }
        val chbOtherAddress = findViewById<CheckBox>(R.id.chbOtherAddress)
        val addressField = findViewById<EditText>(R.id.editTextTextMultiLine2)
        chbOtherAddress.setOnClickListener{
            addressField.isFocusableInTouchMode = chbOtherAddress.isChecked
        }
    }
}