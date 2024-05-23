package com.alikhan.projecttrial

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class ActivityDate : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)

        database = FirebaseDatabase.getInstance().reference.child("Orders")

        // Receive the subtotal from the Intent extras
        val subtotal = intent.getStringExtra("subtotal")?.toDoubleOrNull() ?: 0.0

        // Hardcoded delivery charge
        val deliveryCharge = 5.0

        // Calculate the total by adding subtotal and delivery charge
        val total = subtotal + deliveryCharge

        // Update the priceSubtotal TextView with the received subtotal
        val priceSubtotalTextView: TextView = findViewById(R.id.priceSubtotal)
        priceSubtotalTextView.text = "$$subtotal"

        // Update the priceDeliveryCharge TextView with the hardcoded delivery charge
        val priceDeliveryChargeTextView: TextView = findViewById(R.id.priceDeliveryCharge)
        priceDeliveryChargeTextView.text = "$$deliveryCharge"

        // Update the priceTotal TextView with the calculated total
        val priceTotalTextView: TextView = findViewById(R.id.priceTotal)
        priceTotalTextView.text = "$$total"

        val placeOrderButton: Button = findViewById(R.id.buttonAddMore)

        // Set click listener for the "Place Order" button
        placeOrderButton.setOnClickListener {
            // Store the order information in Firebase
            storeOrder(total)

            // Navigate to MainActivity7 and pass the data
            val intent1 = Intent(this@ActivityDate, MainActivity7::class.java)

            intent1.putExtra("selectedDate", selectedDate)
            intent1.putExtra("totalCharges", total)
            startActivity(intent1)
        }

        val spinnerDate: Spinner = findViewById(R.id.spinnerDate)

        // Create an array of date options (e.g., for the next 7 days)
        val dateOptions = arrayOf(
            "Select Date",
            "April 12, 2024",
            "April 13, 2024",
            "April 14, 2024",
            "April 15, 2024",
            "April 16, 2024",
            "April 17, 2024",
            "April 18, 2024"
        )

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dateOptions)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        spinnerDate.adapter = adapter

        // Set a listener to handle item selection
        spinnerDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedDate = dateOptions[position]
                // Handle the selected date (e.g., display it, use it for further processing)
                Toast.makeText(
                    this@ActivityDate,
                    "Selected Date: $selectedDate",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected
            }
        }
    }

    private fun storeOrder(total: Double) {
        // Generate a unique key for the order
        val orderId = database.push().key ?: ""

        // Create an Order object with the total amount and selected date
        val order = Order(orderId, total, selectedDate)

        // Store the order in the "Orders" table
        database.child(orderId).setValue(order)
            .addOnSuccessListener {
                // Order stored successfully
                Toast.makeText(
                    this@ActivityDate,
                    "Order placed successfully",
                    Toast.LENGTH_SHORT
                ).show()
                val notificationRef = FirebaseDatabase.getInstance().reference.child("notifications").push()
                val notificationMessage = "Message:"
                val notification = mapOf(
                    "message" to notificationMessage,
                    "timestamp" to ServerValue.TIMESTAMP
                )
                notificationRef.setValue(notification)
                val myFirebaseMessagingService = MyFirebaseMessagingService()
                myFirebaseMessagingService.generateNotification(this,"Quick Cart", " Login Successful " )
            }
            .addOnFailureListener {
                // Failed to store order
                Toast.makeText(
                    this@ActivityDate,
                    "Failed to place order",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}

// Data class representing an order
data class Order(
    val orderId: String = "",
    val total: Double = 0.0,
    val selectedDate: String
)
