package com.alikhan.projecttrial



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Button

class FilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_filer)

        val applyFiltersButton: Button = findViewById(R.id.SignUpButton)
        applyFiltersButton.setOnClickListener {
            applyFilters()
        }
    }

    private fun applyFilters() {
        val typeOption1: CheckBox = findViewById(R.id.typeOption1)
        val typeOption2: CheckBox = findViewById(R.id.typeOption2)
        val typeOption3: CheckBox = findViewById(R.id.typeOption3)
        val typeOption4: CheckBox = findViewById(R.id.typeOption4)
        val typeOption5: CheckBox = findViewById(R.id.typeOption5)
        val typeOption6: CheckBox = findViewById(R.id.typeOption6)

        val selectedTypes = StringBuilder()

        if (typeOption1.isChecked) {
            selectedTypes.append("Fruits")
        }
        if (typeOption2.isChecked) {
            if (selectedTypes.isNotEmpty()) {
                selectedTypes.append(", ")
            }
            selectedTypes.append("Breakfast")
        }
        if (typeOption3.isChecked) {
            if (selectedTypes.isNotEmpty()) {
                selectedTypes.append(", ")
            }
            selectedTypes.append("Beverages")
        }
        if (typeOption4.isChecked) {
            if (selectedTypes.isNotEmpty()) {
                selectedTypes.append(", ")
            }
            selectedTypes.append("Meat")
        }
        if (typeOption5.isChecked) {
            if (selectedTypes.isNotEmpty()) {
                selectedTypes.append(", ")
            }
            selectedTypes.append("Snacks")
        }
        if (typeOption6.isChecked) {
            if (selectedTypes.isNotEmpty()) {
                selectedTypes.append(", ")
            }
            selectedTypes.append("Dairy")
        }

        val intent = Intent(this, SearchpageActivity::class.java)
        intent.putExtra("selectedTypes", selectedTypes.toString())
        startActivity(intent)
    }
}