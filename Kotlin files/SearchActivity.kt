package com.alikhan.projecttrial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        recyclerView = findViewById(R.id.searchrecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Create dummy data for testing
        val itemList = listOf(
            Snacks(R.drawable.img, "Arla DANO Full Cream Milk Powder Instant", "Rs 182"),
            Snacks(R.drawable.img_1, "Another Snack Name", "Rs 200"),
            // Add more items as needed
        )

        // Initialize the adapter with the dummy data
        adapter = SearchAdapter(this, itemList)

        // Set the adapter to the RecyclerView
        recyclerView.adapter = adapter


    }
}