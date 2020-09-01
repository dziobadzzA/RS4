package com.example.satapp.ui.main

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import coil.api.load
import com.example.satapp.R

class ImagePager (url: String): Fragment(), View.OnClickListener {

    var sortir: Array<String>? = url.split(" ").toTypedArray()
    private var positionImage: Int? = null
    private var vidView: ImageView? = null

    @NonNull
    @Nullable
    @Override
    override fun onCreateView(@NonNull inflater: LayoutInflater, @NonNull container: ViewGroup?, @NonNull savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.imagefragment, container, false)
        vidView = view.findViewById(R.id.imageView2)
        retainInstance = true
        val ButtonImage: ImageView = view.findViewById(R.id.imageView2)
        ButtonImage.setOnClickListener(this)

        savedInstanceState?.let { onSaveInstanceState(it) }

        if (sortir != null) { if (sortir!!.isNotEmpty()) display(0); positionImage = 0; }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArray("urlsave", sortir)
        outState.putInt("CurrentPosition", positionImage!!)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (savedInstanceState != null) {
            sortir = savedInstanceState!!.getStringArray("urlsave")
            positionImage = savedInstanceState?.getInt("CurrentPosition");
        }

        if (positionImage!= null) { display(positionImage!!);}
    }

    fun display(position: Int)
    {
        vidView!!.load(Uri.parse(sortir!![position]))
    }

    override fun onClick(v: View?) {
        if (positionImage!= null)
        {
            positionImage = positionImage!! + 1
            if (positionImage == sortir!!.size) {positionImage = 0}
            display(positionImage!!)
        }
    }

}