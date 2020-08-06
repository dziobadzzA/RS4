@file:Suppress("DEPRECATION")

package com.example.cattaskapp

import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.util.TypedValue
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import kotlinx.android.synthetic.main.activity_channel_r_s_s.*

class fragmentfilter : PreferenceFragment() {

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferenceexample);
    }

}