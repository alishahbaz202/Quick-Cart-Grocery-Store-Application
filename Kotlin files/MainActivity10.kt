package com.alikhan.projecttrial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity10 : AppCompatActivity() {

    private lateinit var reviewEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var skipButton: Button
    private lateinit var ratingBar: RatingBar
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main10)

        reviewEditText = findViewById(R.id.reviewEditTextt)
        submitButton = findViewById(R.id.SubmitButton)
        skipButton = findViewById(R.id.SkipButton)
        ratingBar = findViewById(R.id.ratingBar)
        database = FirebaseDatabase.getInstance().reference.child("reviews")

        submitButton.setOnClickListener {
            val reviewText = reviewEditText.text.toString().trim()
            val rating = ratingBar.rating
            if (reviewText.isNotEmpty()) {
                // Store review and rating in Firebase
                storeReviewAndRating(reviewText, rating)
                // Show toast
                Toast.makeText(this, "Review submitted successfully!", Toast.LENGTH_SHORT).show()
            } else {
                // Show error toast if review is empty
                Toast.makeText(this, "Please write a review first", Toast.LENGTH_SHORT).show()
            }
        }

        skipButton.setOnClickListener {
            val intent = Intent(this, MainActivity11::class.java)
            startActivity(intent)
        }
    }

    private fun storeReviewAndRating(review: String, rating: Float) {
        val reviewId = database.push().key
        reviewId?.let {
            val reviewData = HashMap<String, Any>()
            reviewData["review"] = review
            reviewData["rating"] = rating
            database.child(it).setValue(reviewData)
        }
    }
}