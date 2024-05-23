package com.alikhan.projecttrial

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alikhan.projecttrial.MainActivity2
import com.alikhan.projecttrial.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val loginIntent = Intent(this, MainActivity2::class.java)
            startActivity(loginIntent)
            finish()


        }, 1500)
    }
}
