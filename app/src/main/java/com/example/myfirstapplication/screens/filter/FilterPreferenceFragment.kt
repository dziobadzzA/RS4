package com.example.myfirstapplication.screens.filter

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.myfirstapplication.R

class FilterPreferenceFragment : PreferenceFragmentCompat() {
    
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferenceexample)
    }

}