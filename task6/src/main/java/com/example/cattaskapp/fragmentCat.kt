package com.example.cattaskapp

import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_channel_r_s_s.*

@Suppress("DEPRECATION")
class fragmentCat : AppCompatActivity() {

    companion object
    {
        const val urlsave = "urlsave"
    }

    var sortir: String? = null
    private var position = 0
    private var vidView: VideoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vid)

        sortir = intent.getStringExtra("urlsave")
        vidView = findViewById<VideoView>(R.id.videoView2)
        display()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("urlsave", sortir)
        vidView?.getCurrentPosition()?.let { outState.putInt("CurrentPosition", it) };
        vidView?.isPlaying?.let { outState.putBoolean("Play", it) };
        vidView?.pause();
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        sortir = savedInstanceState.get("urlsave").toString()
        position = savedInstanceState.getInt("CurrentPosition");
        vidView?.seekTo(position)

        if (savedInstanceState.getBoolean("Play")) { vidView?.start()}
    }

    fun display()
    {

        var relVideo = sortir!!.split("|")

        var vidtest:Uri = Uri.parse(relVideo[0])

        var mediaController: MediaController = MediaController(this)

        vidView?.requestFocus(0)
        vidView?.setMediaController(mediaController)
        vidView?.setVideoURI(vidtest)
        mediaController.setAnchorView(vidView)
        var textView = findViewById<TextView>(R.id.textView6)
        textView.text =  relVideo[1]

        var textViewAutor = findViewById<TextView>(R.id.textView)
        textViewAutor.text =  relVideo[2]

        var textViewTitle = findViewById<TextView>(R.id.textView4)
        textViewTitle.text =  relVideo[3]

    }


}
