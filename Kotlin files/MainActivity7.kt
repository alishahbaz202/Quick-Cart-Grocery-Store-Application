package com.alikhan.projecttrial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MainActivity7 : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private var orderList: MutableList<OrderDisplayy> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)

        database = FirebaseDatabase.getInstance().reference.child("Orders")

        orderRecyclerView = findViewById(R.id.orderRecyclerView)
        orderRecyclerView.layoutManager = LinearLayoutManager(this)
        orderAdapter = OrderAdapter(orderList)
        orderRecyclerView.adapter = orderAdapter

        // Retrieve data from Firebase Database
        retrieveOrders()
    }

    private fun retrieveOrders() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orderList.clear()

                for (orderSnapshot in snapshot.children) {
                    val selectedDate = orderSnapshot.child("selectedDate").getValue(String::class.java) ?: ""

                    // Set total as a static value (e.g., 20)
                    val total = " Rs 20"

                    // Static image resource (assuming R.drawable.your_image is your static image)
                    val imageResource = R.drawable.orderimage

                    // Static order ID
                    val staticOrderId = "Order #345"

                    // Set "delivered" as the default value for delivered attribute
                    val delivered = "delivered"

                    val orderItem = OrderDisplayy(staticOrderId, delivered, selectedDate, total, imageResource)
                    orderList.add(orderItem)
                }

                orderAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }

}