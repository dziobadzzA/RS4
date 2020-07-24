package com.example.cattaskapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cattaskapp.adapter.CatAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity()  {

    private val itemAdapter = CatAdapter()

    private val catViewModel by viewModels<CatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        catViewModel.items.observe(this, Observer {
            it ?: return@Observer
            itemAdapter.addItems(it)
        })

    }

    fun clickanimation(view:View)
    {
        val sendimage = view as ImageView
        var intent = Intent(this, fragmentCat::class.java)
        intent.putExtra(fragmentCat.urlsave, sendimage.tag.toString())
        startActivity(intent)
    }




}


