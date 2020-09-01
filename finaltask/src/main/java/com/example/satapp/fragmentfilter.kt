@file:Suppress("DEPRECATION")

package com.example.satapp

import android.os.Bundle
import android.preference.PreferenceFragment

class fragmentfilter : PreferenceFragment() {

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferenceexample)

    }

}