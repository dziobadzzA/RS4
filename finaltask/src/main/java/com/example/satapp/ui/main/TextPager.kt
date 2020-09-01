package com.example.satapp.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.satapp.R

class TextPager(outtext: String): Fragment() {

    var sortir: String? = outtext
    private var vidView: TextView? = null

    @NonNull
    @Nullable
    @Override
    override fun onCreateView(@NonNull inflater: LayoutInflater, @NonNull container: ViewGroup?, @NonNull savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.textfragment, container, false)
        vidView = view.findViewById(R.id.textViewFragment)
        retainInstance = true
        savedInstanceState?.let { onSaveInstanceState(it) }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("text", sortir)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            sortir = savedInstanceState.get("text").toString()
        }
        display()
    }

    fun display()
    {
        vidView!!.text  = sortir
    }


}
