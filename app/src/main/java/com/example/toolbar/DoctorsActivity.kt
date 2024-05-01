package com.example.toolbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class DoctorsActivity : AppCompatActivity(), PaymentResultListener {
    lateinit var bookseat1 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctors)
        bookseat1 = findViewById(R.id.bookseat1)
        bookseat1.setOnClickListener {
            makePayment()
        }
    }

    private fun makePayment() {

            val co = Checkout()

            try {
                val options = JSONObject()
                options.put("name","Blood Unity")
                options.put("description","Blood Donation App")

                options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
                options.put("theme.color", "#3399cc");
                options.put("currency","INR");
                options.put("amount","50000")



                val prefill = JSONObject()
                prefill.put("email","ayush@gmail.com")
                prefill.put("contact","")

                options.put("prefill",prefill)
                co.open(this,options)
            }catch (e: Exception){
                Toast.makeText(this,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
                e.printStackTrace()

        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Payment Successful $p0",Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Payment Failed $p0 ",Toast.LENGTH_SHORT).show()
    }
}