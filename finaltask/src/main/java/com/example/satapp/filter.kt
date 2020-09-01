package com.example.satapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

@Suppress("DEPRECATION")
class filter : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        fragmentManager.beginTransaction().replace(android.R.id.content, fragmentfilter()).commit()
        supportActionBar!!.hide()
    }
}