package com.alikhan.projecttrial

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class FifthActivity : AppCompatActivity(), SnacksAdapter.OnItemClickListener {
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SnacksAdapter
    private val itemList: MutableList<AddItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth_page)

        val category = intent.getStringExtra("categoryName") ?: ""
        database = FirebaseDatabase.getInstance().reference.child("items").child(category)

        recyclerView = findViewById(R.id.snacksrecycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = SnacksAdapter(itemList, this)
        recyclerView.adapter = adapter

        loadItemsFromFirebase()
    }

    private fun loadItemsFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(AddItem::class.java)
                    item?.let { itemList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database query cancellation or errors
            }
        })
    }

    override fun onItemClick(item: AddItem) {
        // Store the information of the clicked item into Firebase



                // After storing the item information, navigate to ActivityBag and pass the item details as extras
                val intent = Intent(this@FifthActivity, ActivityBag::class.java).apply {
                    putExtra("itemName", item.name)
                    putExtra("itemPrice", item.price)
                    putExtra("itemImageUrl", item.imageUrl)
                }
                startActivity(intent)
            }

}

