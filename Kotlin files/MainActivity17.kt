package com.alikhan.projecttrial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity17 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main17)
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
                    startActivity(Intent(this, ActivityBag::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    true
                }
                else -> false
            }
        }
        // Initialize Firebase Authentication
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val currentUser: FirebaseUser? = auth.currentUser

        // Initialize Firebase Database
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.reference.child("Users")

        // Find views
        val profileImageView: ImageView = findViewById(R.id.profilepictureView)
        val nameTextView: TextView = findViewById(R.id.nametext)

        // Check if the user is logged in
        if (currentUser != null) {
            // Get the current user's email
            val email: String = currentUser.email ?: ""

            // Use the email to fetch the user's data from Firebase
            usersRef.child(email.replace(".", ",")).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get the user object
                    val user: Signup? = dataSnapshot.getValue(Signup::class.java)

                    // Check if user data is not null
                    if (user != null) {
                        // Set the user's name
                        nameTextView.text = user.name

                        // Set the user's profile image (if available)
                        if (user.imageUrl.isNotEmpty()) {
                            Glide.with(this@MainActivity17)
                                .load(user.imageUrl)
                                .into(profileImageView)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle database error
                }
            })
        }





        val Chatwithustext = findViewById<TextView>(R.id.Chatwithustext)

        Chatwithustext.setOnClickListener {
            val intent = Intent(this, MainActivity9::class.java)
            startActivity(intent)
        }




        val EditProfiletext = findViewById<TextView>(R.id.EditProfiletext)

        EditProfiletext.setOnClickListener {
            val intent = Intent(this, MainActivity16::class.java)
            startActivity(intent)
        }


        val MyAddresstext = findViewById<TextView>(R.id.MyAddresstext)

        MyAddresstext.setOnClickListener {
            val intent = Intent(this, MainActivity11::class.java)
            startActivity(intent)
        }

        val MyOrderstext = findViewById<TextView>(R.id.MyOrderstext)

        MyOrderstext.setOnClickListener {
            val intent = Intent(this, MainActivity7::class.java)
            startActivity(intent)
        }


        val MyWishlisttext = findViewById<TextView>(R.id.MyWishlisttext)

        MyWishlisttext.setOnClickListener {
            val intent = Intent(this, MainActivity13::class.java)
            startActivity(intent)
        }

        val TalktoourSupporttext = findViewById<TextView>(R.id.TalktoourSupporttext)

        TalktoourSupporttext.setOnClickListener {
            val intent = Intent(this, MainActivity9::class.java)
            startActivity(intent)
        }

        val Logouttext = findViewById<TextView>(R.id.Logouttext)

        Logouttext.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        val AddWishlist = findViewById<TextView>(R.id.Messagetofacebookpagetext)

        AddWishlist.setOnClickListener {
            val intent = Intent(this, MainActivity15::class.java)
            startActivity(intent)
        }


        val submitfeedbacktext = findViewById<TextView>(R.id.submitfeedbacktext)

        submitfeedbacktext.setOnClickListener {
            val intent = Intent(this, MainActivity10::class.java)
            startActivity(intent)
        }

        val searchsomethingtext = findViewById<TextView>(R.id.searchsomethingtext)

        searchsomethingtext.setOnClickListener {
            val intent = Intent(this, MainActivity8::class.java)
            startActivity(intent)
        }

    }
}
