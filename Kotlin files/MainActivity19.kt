package com.alikhan.projecttrial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity19 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main19)
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        // Set item selected listener
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MainActivity18::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_category -> {
                    true

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

        val fruitsVegetables: ImageView = findViewById(R.id.FruitsVegetables)
        val breakfast: ImageView = findViewById(R.id.Breakfast)
        val beverages: ImageView = findViewById(R.id.Beverages)
        val meat: ImageView = findViewById(R.id.Meat)
        val snacks: ImageView = findViewById(R.id.Snacks)
        val dairy: ImageView = findViewById(R.id.Dairy)




        fruitsVegetables.setOnClickListener {
            sendCategoryNameAndNavigate("Fruits and Vegetables")
        }

        breakfast.setOnClickListener {
            sendCategoryNameAndNavigate("Breakfast")
        }

        beverages.setOnClickListener {
            sendCategoryNameAndNavigate("Beverages")
        }

        meat.setOnClickListener {
            sendCategoryNameAndNavigate("Meat")
        }

        snacks.setOnClickListener {
            sendCategoryNameAndNavigate("Snacks")
        }

        dairy.setOnClickListener {
            sendCategoryNameAndNavigate("Dairy")
        }




    }

    private fun sendCategoryNameAndNavigate(categoryName: String) {
        val intent = Intent(this, FifthActivity::class.java)
        intent.putExtra("categoryName", categoryName)
        startActivity(intent)
    }
}