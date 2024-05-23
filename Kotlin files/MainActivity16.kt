package com.alikhan.projecttrial

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class MainActivity16 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var currentUser: FirebaseUser

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main16)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!
        databaseReference = FirebaseDatabase.getInstance().reference.child("Users")
            .child(currentUser.email!!.replace(".", ","))

        val saveButton = findViewById<Button>(R.id.saveButton)
        val fullNameEditText = findViewById<EditText>(R.id.fullnameEditTextt)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditTextt)
        val emailEditText = findViewById<EditText>(R.id.phonenumberEditTextt)
        val profilePictureView = findViewById<ImageView>(R.id.profilepictureView)

        // Retrieve user details from Firebase
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(Signup::class.java)
                if (user != null) {
                    fullNameEditText.setText(user.name)
                    passwordEditText.setText(user.password)
                    emailEditText.setText(user.email)
                    // Load profile picture using Glide or any other library
                    if (user.imageUrl.isNotEmpty()) {
                        Glide.with(this@MainActivity16).load(user.imageUrl).into(profilePictureView)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@MainActivity16,
                    "Failed to retrieve user details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        // Button to select profile picture
        val selectPictureButton = findViewById<ImageView>(R.id.editprofilepictureView)
        selectPictureButton.setOnClickListener {
            openImageChooser()
        }

        saveButton.setOnClickListener {
            // Update user details in Firebase Database
            val newName = fullNameEditText.text.toString().trim()
            val newPassword = passwordEditText.text.toString().trim()
            val newEmail = emailEditText.text.toString().trim()

            // Validate input if needed

            // Upload the image to Firebase Storage
            selectedImageUri?.let { imageUri ->
                val storageRef = FirebaseStorage.getInstance().reference
                val imagesRef = storageRef.child("profile_images").child(currentUser.uid)
                val imageFileName = "profile_image.jpg"
                val imageRef = imagesRef.child(imageFileName)

                imageRef.putFile(imageUri)
                    .addOnSuccessListener { taskSnapshot ->
                        // Image uploaded successfully, get the download URL
                        imageRef.downloadUrl.addOnSuccessListener { uri ->
                            val newProfilePictureUrl = uri.toString()

                            // Update user details in Firebase Database with the new image URL
                            databaseReference.child("name").setValue(newName)
                            databaseReference.child("password").setValue(newPassword)
                            databaseReference.child("email").setValue(newEmail)
                            databaseReference.child("imageUrl").setValue(newProfilePictureUrl)

                            // Update user email and password in Firebase Authentication
                            currentUser.updateEmail(newEmail)
                                .addOnSuccessListener {
                                    // Email updated successfully
                                }
                                .addOnFailureListener { e ->
                                    // An error occurred while updating email
                                }

                            currentUser.updatePassword(newPassword)
                                .addOnSuccessListener {
                                    // Password updated successfully
                                }
                                .addOnFailureListener { e ->
                                    // An error occurred while updating password
                                }

                            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

            } ?: run {
                // No image selected
                // Proceed to update other user details without updating the image
                databaseReference.child("name").setValue(newName)
                databaseReference.child("password").setValue(newPassword)
                databaseReference.child("email").setValue(newEmail)

                // Update user email and password in Firebase Authentication
                currentUser.updateEmail(newEmail)
                    .addOnSuccessListener {
                        // Email updated successfully
                    }
                    .addOnFailureListener { e ->
                        // An error occurred while updating email
                    }

                currentUser.updatePassword(newPassword)
                    .addOnSuccessListener {
                        // Password updated successfully
                    }
                    .addOnFailureListener { e ->
                        // An error occurred while updating password
                    }

                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            selectedImageUri = data?.data
            // Display the selected image in ImageView
            val profilePictureView = findViewById<ImageView>(R.id.profilepictureView)
            profilePictureView.setImageURI(selectedImageUri)
        }
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }
}