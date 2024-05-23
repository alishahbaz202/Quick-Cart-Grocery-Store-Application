package com.alikhan.projecttrial

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class GuestCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guestcategory_page)


        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        // Set item selected listener
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, HomeGuest::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_category -> {
                    true

                }
                R.id.navigation_shop -> {
                    startActivity(Intent(this, GuestBag::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }


    }
}