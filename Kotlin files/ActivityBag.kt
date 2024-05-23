package com.alikhan.projecttrial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityBag : AppCompatActivity() {
    private lateinit var itemList: MutableList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bag)
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        // Set item selected listener
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MainActivity18::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_category -> {
                    startActivity(Intent(this, MainActivity19::class.java))
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.navigation_add -> {
                    startActivity(Intent(this, AddActivity::class.java))
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.navigation_shop -> {
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, MainActivity17::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Retrieve item details from intent extras
        val itemName = intent.getStringExtra("itemName") ?: ""
        val itemPrice = intent.getStringExtra("itemPrice") ?: ""
        val itemImageUrl = intent.getStringExtra("itemImageUrl") ?: ""

        // Create a list with the retrieved item details
        itemList = mutableListOf(
            Item(itemName, itemPrice, itemImageUrl, 1) // Add the clicked item with its details
        )

        // Set up the adapter with the updated item list
        val adapter = CustomAdapter(itemList) { updateTotalPrice() }
        recyclerView.adapter = adapter

        val backArrow: Button = findViewById(R.id.buttonConfirmOrder)
        backArrow.setOnClickListener {
            // Start ActivityDate and pass the subtotal
            val subtotal = calculateTotalPrice()
            val intent = Intent(this@ActivityBag, ActivityDate::class.java)
            intent.putExtra("subtotal", subtotal.toString())
            startActivity(intent)
        }
    }

    private fun calculateTotalPrice(): Double {
        var totalPrice = 0.0
        for (item in itemList) {
            totalPrice += item.price.toDouble() * item.quantity
        }
        return totalPrice
    }

    private fun updateTotalPrice() {
        val totalPrice = calculateTotalPrice()
        // Update subtotal TextView with the new total price
        findViewById<TextView>(R.id.priceSubtotal).text = "$$totalPrice"
    }
}
