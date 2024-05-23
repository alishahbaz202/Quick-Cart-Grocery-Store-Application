package com.alikhan.projecttrial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class MainActivity2 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth // Declare FirebaseAuth variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        auth = FirebaseAuth.getInstance()



        val email = findViewById<EditText>(R.id.emailEditText)
        val password = findViewById<EditText>(R.id.passwordEditText)


        // Find the "Sign Up" button in your layout
        val signUpButton: TextView = findViewById(R.id.signUpTextView)

        // Set a click listener for the "Sign Up" button
        signUpButton.setOnClickListener {
            // Create an Intent to start the ThirdActivity
            val intent = Intent(this@MainActivity2, MainActivity3::class.java)

            // Start the ThirdActivity
            startActivity(intent)
        }

        // Find the "Forgot Password" button in your layout
        val forgotPasswordButton: TextView = findViewById(R.id.forgotPasswordText)

        // Set a click listener for the "Forgot Password" button
        forgotPasswordButton.setOnClickListener {
            // Create an Intent to start the FourthActivity
            val intent = Intent(this@MainActivity2, MainActivity4::class.java)

            // Start the FourthActivity
            startActivity(intent)
        }

        // Find the "Login" button in your layout
        val loginButton: Button = findViewById(R.id.loginButton)

        // Set a click listener for the "Login" button
        loginButton.setOnClickListener {
            val userEmail = email.text.toString().trim()
            val userPass = password.text.toString().trim()

            if (userEmail.isEmpty() || userPass.isEmpty())
            {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
            else
            {
                userSignIn(userEmail, userPass)
            }
        }








    }


    private fun userSignIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()

                    val notificationRef = FirebaseDatabase.getInstance().reference.child("notifications").push()
                    val notificationMessage = "Message:"
                    val notification = mapOf(
                        "message" to notificationMessage,
                        "timestamp" to ServerValue.TIMESTAMP
                    )
                    notificationRef.setValue(notification)
                    val myFirebaseMessagingService = MyFirebaseMessagingService()
                    myFirebaseMessagingService.generateNotification(this,"Quick Cart", " Login Successful " )

                    val intent = Intent(this, MainActivity18::class.java)
                    startActivity(intent)
                } else {
                    // Log the authentication failure
                    Log.e("Authentication", "Authentication failed: ${task.exception?.message}")

                    // Show a Toast message for authentication failure
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }


}