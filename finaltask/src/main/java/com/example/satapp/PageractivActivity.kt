package com.example.satapp

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Color.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.satapp.ui.main.SectionsPagerAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout


@Suppress("DEPRECATION")
class PageractivActivity : AppCompatActivity() {

    companion object
    {
        const val par1 = "beacon"
        const val par2 = "TV"
        const val par3 = "TTX"
        const val par4 = "nameSat"
        const val par5 = "video"
        const val par6 = "coverage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pageractiv)

        var inArray = intent.getStringArrayExtra("mk")
        var sortirTheme = intent.getStringExtra("stylemk")
        homeDisplay(sortirTheme)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, inArray)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }


    @SuppressLint("ResourceAsColor", "ResourceType")
    fun homeDisplay(sorTheme: String)
    {

        val backgroundView: AppBarLayout = findViewById(R.id.appTab)
        val backgroundTab: TabLayout = findViewById(R.id.tabs)
        val backgroundLayout: CoordinatorLayout = findViewById(R.id.layout2)

        var color:Int = 0
        var colorBack:Int = 0

        if (sorTheme == "Dark")
        {
            setTheme(R.style.AppTheme)
            color = R.color.colorPrimary
            colorBack = R.color.colorPrimaryDark
        }
        else
        {
            setTheme(R.style.AppThemeLight)
            color = R.color.colorPrimaryDark
            colorBack = R.color.colorPrimary
        }

        backgroundView.setBackgroundColor(ContextCompat.getColor(this, color))
        backgroundTab.setBackgroundColor(ContextCompat.getColor(this, color))
        backgroundLayout.setBackgroundColor(ContextCompat.getColor(this, colorBack))
        ThemeUpdate(color)

    }

    fun ThemeUpdate(color: Int)
    {
        var typedValue: TypedValue = TypedValue()
        var theme: Resources.Theme = theme

        theme.resolveAttribute(android.R.attr.windowBackground, typedValue, true)

        theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(typedValue.data))

        theme.resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true)
        var window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = typedValue.data
    }

}