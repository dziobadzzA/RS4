package com.example.satapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.toColor
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.satapp.adapter.DataAdapter
import com.example.satapp.data.Sat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import kotlin.math.absoluteValue

@Suppress("RedundantOverride", "DEPRECATION")
class MainActivity: AppCompatActivity() {

    private var mAuth:FirebaseAuth? = FirebaseAuth.getInstance()
	var sortirTheme: String = "Dark"

    companion object
    {
        const val MAIL = "MAIL"
    }

    var sortirItem = "name"
    private var itemAdapter = DataAdapter(this, sortirItem)
    private val satViewModel by viewModels<SatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemAdapter = DataAdapter(this, sortirItem)
        homeDisplay()

        recyclerView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        satViewModel.items.observe(this, Observer { it ->
            it ?: return@Observer
            itemAdapter.addItems(it)
        })

    }

    fun clickOut(view: View) { signOut(mAuth) }

    override fun onResume() {
        homeDisplay()
        super.onResume()
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    fun homeDisplay()
    {

        var prefx = PreferenceManager.getDefaultSharedPreferences(this)
        sortirTheme= prefx.getString("otbor", "Dark").toString()
        sortirItem= prefx.getString("sortir", "name")!!

        var colorBack: Int = 0
        var color:Int = 0
        val backLayot: ConstraintLayout = findViewById(R.id.layout1)
        val imageButton:ImageButton = findViewById(R.id.imageButton)

        if(sortirTheme == "Dark")
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

        imageButton.setBackgroundColor(ContextCompat.getColor(this, colorBack))
        backLayot.setBackgroundColor(ContextCompat.getColor(this, colorBack))
        ThemeUpdate(color)

    }

    fun ThemeUpdate(color: Int)
    {
        var typedValue: TypedValue = TypedValue()
        var theme:Resources.Theme = theme

        val FloatBut:FloatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        theme.resolveAttribute(android.R.attr.windowBackground, typedValue, true)

        theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(typedValue.data))

        FloatBut.backgroundTintList = baseContext.resources.getColorStateList(color)

        theme.resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true)
        var window:Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = typedValue.data
    }

    fun signOut(mAuth: FirebaseAuth?)
    {
        val dialog = CustomDialogFragment(mAuth)
        dialog.show(supportFragmentManager, "custom")
    }

    fun filt(view: View)
    {
        val putEmail = intent.getStringExtra("MAIL")
        var intent = Intent(this, filter::class.java)

        var prefx = PreferenceManager.getDefaultSharedPreferences(this)
        val text = prefx.edit()
        text.putString("mail", putEmail)
        text.apply()

        startActivity(intent)
    }

    fun clickSat(view: View)
    {
        var intent = Intent(this, PageractivActivity::class.java)

        var inItem =  itemAdapter.getItem()
        var index: Int? = null
        for(i in inItem.indices) { if (inItem[i].getName() == view.tag) {index = i; break;} }

        var outItem:Array<String?> = emptyArray<String?>()

        inItem[index!!].getUrl()?.let { outItem += it }
        inItem[index!!].getBeacon()?.let { outItem += it }
        inItem[index!!].getTV()?.let { outItem += it }
        inItem[index!!].getTTX()?.let { outItem += it }
        inItem[index!!].getNamesat()?.let { outItem += it }
        inItem[index!!].getAntensignal()?.let { outItem += it }
        inItem[index!!].getName()?.let { outItem += it }
        inItem[index!!].getFirname()?.let { outItem += it }
        inItem[index!!].getPoint()?.let { outItem += it }

        intent.putExtra("mk", outItem)
        intent.putExtra("stylemk", sortirTheme)

        startActivity(intent)
    }


}


