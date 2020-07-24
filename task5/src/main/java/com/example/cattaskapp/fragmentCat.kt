package com.example.cattaskapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import coil.api.load
import java.io.File

@Suppress("DEPRECATION")
class fragmentCat : AppCompatActivity() {

    companion object
    {
        const val urlsave = "urlsave"
    }

    var sortir: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_cat)

        sortir = intent.getStringExtra("urlsave").toString()

        var imageView = findViewById<ImageView>(R.id.imageView2)
        imageView.load(sortir)

    }


    fun animeclick(view: View)
    {
        var sunImageView =   view as ImageView
        var animationstart = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        //var animationstop = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        sunImageView.startAnimation(animationstart)

        // Handler().postDelayed(Runnable
        // {
        //   sunImageView.startAnimation(animationstop)
        //},2000)

    }

    fun clicks(view: View)
    {
        saveImage(this).execute(sortir)
        var toast: Toast = Toast.makeText(this, "Сохранение", Toast.LENGTH_LONG)
        toast.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("urlsave", sortir)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        sortir = savedInstanceState.get("urlsave").toString()
    }


}