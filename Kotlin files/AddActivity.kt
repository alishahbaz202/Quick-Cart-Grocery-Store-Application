package com.alikhan.projecttrial

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddActivity : AppCompatActivity() {

    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private lateinit var database: DatabaseReference
    private var selectedImageUri: Uri? = null
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_page)

        dbHelper = DBHelper(this)

        // Retrieve item details from intent extras
        val itemName = intent.getStringExtra("itemName") ?: ""
        val itemPrice = intent.getStringExtra("itemPrice") ?: ""
        val itemImageUrl = intent.getStringExtra("itemImageUrl") ?: ""

        // Insert the item into the database
        insertItem(itemName, itemPrice, itemImageUrl, 1)

        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference.child("item_images")
        database = FirebaseDatabase.getInstance().reference.child("items")

        val Submitbutton: Button = findViewById(R.id.buttonAdd)
        Submitbutton.setOnClickListener {
            val nameInput = findViewById<EditText>(R.id.editTextName).text.toString()
            val priceInput = findViewById<EditText>(R.id.editTextDescription).text.toString()
            val categoryInput = findViewById<EditText>(R.id.editTextCategory).text.toString()

            if (selectedImageUri != null && nameInput.isNotEmpty() && priceInput.isNotEmpty() && categoryInput.isNotEmpty()) {
                uploadImageAndItem(categoryInput, nameInput, priceInput)
            } else {
                Toast.makeText(this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show()
            }
        }

        val CameraButton: ImageButton = findViewById(R.id.buttonUploadPhoto)
        CameraButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            selectedImageUri = data?.data
        }
    }

    private fun uploadImageAndItem(category: String, name: String, price: String) {
        selectedImageUri?.let { imageUri ->
            val imageRef = storageRef.child("${System.currentTimeMillis()}.jpg")
            val uploadTask = imageRef.putFile(imageUri)

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

                    val item = AddItem(name, price, imageUrl)
                    uploadItemToFirebase(category, item)
                    uploadItemToWebService(category, name, price, imageUrl)
                } else {
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun uploadItemToFirebase(category: String, item: AddItem) {
        val newItemRef = database.child(category).push()

        newItemRef.setValue(item)
            .addOnSuccessListener {
                val notificationRef = FirebaseDatabase.getInstance().reference.child("notifications").push()
                val notificationMessage = "Message:"
                val notification = mapOf(
                    "message" to notificationMessage,
                    "timestamp" to ServerValue.TIMESTAMP
                )
                notificationRef.setValue(notification)
                val myFirebaseMessagingService = MyFirebaseMessagingService()
                myFirebaseMessagingService.generateNotification(this,"Quick Cart", " Item added successfully " )
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to add item to Firebase: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadItemToWebService(category: String, name: String, price: String, imageUrl: String) {
//        val url = "http://" + getString(R.string.server_ip) + "/api/add_item.php"
        val url = "http://192.168.100.18/api/add_item.php"

        val stringRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
            },
            { error ->
                Toast.makeText(this, "Failed to add item to web service: ${error.message}", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["category"] = category
                params["name"] = name
                params["price"] = price
                params["imageUrl"] = imageUrl
                return params
            }
        }

        Volley.newRequestQueue(this).add(stringRequest)
    }
    private fun insertItem(description: String, price: String, imageUrl: String, quantity: Int) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_DESCRIPTION, description)
            put(DBHelper.COLUMN_PRICE, price)
            put(DBHelper.COLUMN_IMAGE_URL, imageUrl)
            put(DBHelper.COLUMN_QUANTITY, quantity)
        }
        db.insert(DBHelper.TABLE_ITEMS, null, values)
        db.close()
    }
}