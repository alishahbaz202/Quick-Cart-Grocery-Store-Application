package com.alikhan.projecttrial

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity15 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main15) // Replace with your actual layout file

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("wishlist")
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference.child("wishlist_images")

        val productNameEditText = findViewById<EditText>(R.id.productnameEditTextt)
        val amountEditText = findViewById<EditText>(R.id.amountEditTextt)
        val brandEditText = findViewById<EditText>(R.id.brandEditTextt)
        val saveButton = findViewById<Button>(R.id.savetowishlistButton)
        val galleryButton = findViewById<ImageView>(R.id.uploadImageView)

        galleryButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(intent)
        }

        saveButton.setOnClickListener {
            val productName = productNameEditText.text.toString().trim()
            val amount = amountEditText.text.toString().trim()
            val brand = brandEditText.text.toString().trim()

            if (productName.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "Please enter product name and amount", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userId = currentUser.uid
                val userEmail = currentUser.email
                val username = userEmail?.substringBefore('@') ?: "Unknown"

                val newItemRef = database.child(userId).push()
                val itemId = newItemRef.key

                if (itemId != null) {
                    // Upload image to Firebase Storage
                    val imageRef = storageRef.child("${System.currentTimeMillis()}.jpg")
                    val uploadTask = imageRef.putFile(selectedImageUri!!)

                    uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        imageRef.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            val imageUrl = downloadUri.toString()

                            // Create WishlistItem instance and save to Firebase Realtime Database
                            val item = WishlistItem(productName, amount, brand, username, imageUrl)
                            newItemRef.setValue(item)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "Item added to wishlist",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val notificationRef = FirebaseDatabase.getInstance().reference.child("notifications").push()
                                    val notificationMessage = "Message:"
                                    val notification = mapOf(
                                        "message" to notificationMessage,
                                        "timestamp" to ServerValue.TIMESTAMP
                                    )
                                    notificationRef.setValue(notification)
                                    val myFirebaseMessagingService = MyFirebaseMessagingService()
                                    myFirebaseMessagingService.generateNotification(this,"Quick Cart", " Item added to wishlist " )
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        this,
                                        "Failed to add item: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        } else {
                            Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Failed to generate item ID", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                // You might want to navigate the user to the login screen here
            }
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                selectedImageUri = data?.data
            }
        }
}