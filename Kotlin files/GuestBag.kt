package com.alikhan.projecttrial


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class GuestBag : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guestbag)


        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        // Set item selected listener
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, HomeGuest::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_category -> {
                    startActivity(Intent(this, GuestCategoryActivity::class.java))
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.navigation_shop -> {
                    true
                }
                else -> false
            }
        }


    }
}