package com.alikhan.projecttrial

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class GuestHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guest_home)


        val toolnavigationView = findViewById<TextView>(R.id.EditProfiletext)

        toolnavigationView.setOnClickListener {
            val intent = Intent(this, HomeGuest::class.java)
            startActivity(intent)
        }



        val Chatwithustext = findViewById<TextView>(R.id.Chatwithustext)

        Chatwithustext.setOnClickListener {
            val intent = Intent(this, MainActivity9::class.java)
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

        val searchsomethingtext = findViewById<TextView>(R.id.searchsomethingtext)

        searchsomethingtext.setOnClickListener {
            val intent = Intent(this, MainActivity8::class.java)
            startActivity(intent)
        }

    }

    }

