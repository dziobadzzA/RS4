package com.example.cattaskapp

import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.TypedValue
import android.view.Window
import android.view.WindowManager

@Suppress("DEPRECATION")
class filter : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        fragmentManager.beginTransaction().replace(android.R.id.content, fragmentfilter()).commit()

    }
}