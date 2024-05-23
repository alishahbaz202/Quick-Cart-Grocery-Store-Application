package com.alikhan.projecttrial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity18 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main18)



        val notificationemojitext = findViewById<TextView>(R.id.notificationemojitext)

        notificationemojitext.setOnClickListener {
            val intent = Intent(this, MainActivity14::class.java)
            startActivity(intent)
        }

        val filter = findViewById<ImageView>(R.id.FilterIcon)
        filter.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            startActivity(intent)
        }


        val searchEditText: EditText = findViewById(R.id.searchEditText)
        val searchIcon: ImageView = findViewById(R.id.SearchIcon)

        searchIcon.setOnClickListener {
            val searchText = searchEditText.text.toString().trim()
            if (searchText.isNotEmpty()) {
                val intent = Intent(this, SearchpageActivity::class.java)
                intent.putExtra("selectedTypes", searchText)
                startActivity(intent)
            } else {
                // Handle empty search text
            }
        }

        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        // Set item selected listener
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    true
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
                    startActivity(Intent(this, ActivityBag::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, MainActivity17::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

    }
}