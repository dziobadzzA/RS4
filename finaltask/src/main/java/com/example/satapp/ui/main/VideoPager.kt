package com.example.satapp.ui.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.satapp.R


class VideoPager(url: String): Fragment() {

    var sortir: String? = url
    private var position = 0
    private var vidView: VideoView? = null
    private var play: Boolean = false


    @NonNull
    @Nullable
    @Override
    override fun onCreateView(@NonNull inflater: LayoutInflater, @NonNull container: ViewGroup?, @NonNull savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.videorss, container, false)
        vidView = view.findViewById(R.id.videoView2)
        retainInstance = true

        if (savedInstanceState != null) {
            sortir = savedInstanceState!!.getString("urlsave")
            position = savedInstanceState.getInt("CurrentPosition")
            play = savedInstanceState?.getBoolean("Play")
        }

        display()
        if (play) { vidView?.start()}
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("urlsave", sortir)
        vidView?.currentPosition?.let { outState.putInt("CurrentPosition", it) };
        vidView?.isPlaying?.let { outState.putBoolean("Play", it) };
        vidView?.pause();
    }

    private fun display()
    {
        vidView?.requestFocus(0)
        var mediaController: MediaController = MediaController(context)
        vidView?.setMediaController(mediaController)
        vidView?.setVideoURI(Uri.parse(sortir))
        mediaController.setAnchorView(vidView)
        vidView?.seekTo(position)

    }

}

