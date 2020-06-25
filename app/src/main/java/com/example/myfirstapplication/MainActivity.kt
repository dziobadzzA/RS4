package com.example.myfirstapplication

import android.content.Context
import android.content.Intent
import android.database.Cursor
import  android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

@Suppress("RedundantOverride", "DEPRECATION")

class MainActivity : AppCompatActivity()
{

    companion object
    {
        const val FILTER = "FILTER"
    }

    var sortir: String? = "name"
    var mentors: MutableList<Mentor> =  mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("FILTER", sortir)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        sortir = savedInstanceState.get("FILTER").toString()
    }

    fun addatabase(view: View)
    {
       var intent = Intent(this, addatabase::class.java)
       startActivity(intent)
    }

    fun filt(view: View)
    {
        var intent = Intent(this, filter::class.java)
        startActivity(intent)
    }

    private fun setInitialData()
    {
        mentors.clear()

        var db: SQLiteDatabase = baseContext.openOrCreateDatabase("app.db", Context.MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, firname TEXT, number TEXT)");

        var query: Cursor = db.rawQuery("SELECT * FROM users ORDER BY $sortir;", null)

        if (query.moveToFirst())
        {
            var index = 0
            do
            {
                val name: String? = query.getString(0)
                val firname: String? = query.getString(1)
                val number: String? = query.getString(2)

                var addMonic: Mentor = Mentor().also {
                    it.Mentor(name, firname, number)
                }

                mentors.add(index, addMonic)
                index++

            } while (query.moveToNext())
        }

        query.close()
        db.close()
    }

    override fun onResume()
    {
        var prefx = PreferenceManager.getDefaultSharedPreferences(this)
        sortir= prefx.getString("otbor", "name")

        setInitialData()

        val recyclerView: RecyclerView = findViewById(R.id.list)
        val adapter = DataAdapter(this, mentors)
        recyclerView.adapter = adapter

        super.onResume()
    }


}
