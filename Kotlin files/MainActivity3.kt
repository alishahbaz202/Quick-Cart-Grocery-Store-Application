package com.alikhan.projecttrial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class MainActivity3 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth // Declare FirebaseAuth variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.emailEditText)
        val password = findViewById<EditText>(R.id.passwordEditText)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val cities = findViewById<EditText>(R.id.cityEditText)
        val countryEditText = findViewById<EditText>(R.id.countryEditText)


        // Find the "Sign Up" button in your layout
        val guest: TextView = findViewById(R.id.alreadyLogInTextView)

        // Set a click listener for the "Sign Up" button
        guest.setOnClickListener {
            // Create an Intent to start the ThirdActivity
            val intent = Intent(this@MainActivity3, GuestHomeActivity::class.java)

            // Start the ThirdActivity
            startActivity(intent)
        }


        val Signup: Button = findViewById(R.id.SignUpButton)
        Signup.setOnClickListener {
            val userEmail = email.text.toString().trim()
            val userPass = password.text.toString().trim()
            val name = nameEditText.text.toString().trim()
            val country = countryEditText.text.toString().trim()
            val city = cities.text.toString().trim()

            if (userEmail.isEmpty() || userPass.isEmpty() || name.isEmpty() || country.isEmpty() || city.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val signupUser = Signup(name, userEmail, country, userPass, city)
                userSignUp(userEmail, userPass, signupUser)
            }
        }
    }

    private fun userSignUp(email: String, password: String, signupUser: Signup) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    // Call the submitData function here, after successful sign up
                    submitData(signupUser)
                    insertIntoXAMPPDatabase(signupUser) // Call function to insert into XAMPP database
                    Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign up fails due to existing email, show error message
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        Toast.makeText(this, "Email already in use", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Sign up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun submitData(signupUser: Signup) {
        Log.d("Firebase", "Submitting data to Firebase: $signupUser")
        val database = FirebaseDatabase.getInstance().reference
        database.child("Users").child(signupUser.email.replace(".", ",")).setValue(signupUser)
            .addOnSuccessListener {
                Toast.makeText(this@MainActivity3, "User registered successfully", Toast.LENGTH_SHORT).show()
                Log.d("Firebase", "Data submitted successfully to Firebase: $signupUser")
                val intent = Intent(this@MainActivity3, MainActivity18::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this@MainActivity3, "Failed to register user: ${it.message}", Toast.LENGTH_SHORT).show()
                Log.e("Firebase", "Failed to submit data to Firebase: ${it.message}")
            }
    }

    private fun insertIntoXAMPPDatabase(signupUser: Signup) {
       // val url = "http://" + getString(R.string.server_ip) + "/api/signup.php" // Replace YOUR_SERVER_IP with your actual server IP
        val url = "http://192.168.100.18/api/signup.php"


        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                // Handle successful response
                Log.d("XAMPP API Response", response)
                // Parse the response JSON if necessary
                // Assuming your PHP script returns a JSON response, handle it here
            },
            Response.ErrorListener { error ->
                // Handle error
                Log.e("XAMPP API Error", "Error occurred: ${error.message}")
                // Display an error message to the user
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                // Add user data to the params
                params["name"] = signupUser.name
                params["email"] = signupUser.email
                params["city"] = signupUser.city
                params["country"] = signupUser.country
                params["password"] = signupUser.password
                return params
            }
        }
        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(stringRequest)
    }
}