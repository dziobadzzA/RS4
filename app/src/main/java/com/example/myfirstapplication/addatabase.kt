package com.example.myfirstapplication

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class addatabase : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addatabase)
    }

    override fun onSaveInstanceState(outState: Bundle) {

        var editTextname:EditText = findViewById(R.id.name)
        var editTextfirname:EditText = findViewById(R.id.firname)
        var editTextnumber:EditText = findViewById(R.id.number)

        super.onSaveInstanceState(outState)
        outState.putString("savename", editTextname.text.toString())
        outState.putString("savefirname", editTextfirname.text.toString())
        outState.putString("savenumber", editTextnumber.text.toString())

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        var editTextname:EditText = findViewById(R.id.name)
        var editTextfirname:EditText = findViewById(R.id.firname)
        var editTextnumber:EditText = findViewById(R.id.number)

        editTextname.setText(savedInstanceState.getString("savename"))
        editTextfirname.setText(savedInstanceState.getString("savefirname"))
        editTextnumber.setText(savedInstanceState.getString("savenumber"))

    }

    fun addbase(view: View)
    {

        var editTextname:EditText = findViewById(R.id.name)
        var editTextfirname:EditText = findViewById(R.id.firname)
        var editTextnumber:EditText = findViewById(R.id.number)

        var db:SQLiteDatabase = baseContext.openOrCreateDatabase("app.db", Context.MODE_PRIVATE, null)

        var query: Cursor = db.rawQuery(
            "SELECT * FROM users WHERE name = '${editTextname.text}' AND firname = '${editTextfirname.text}' AND number = '${editTextnumber.text}';",
            null
        )
        if (!query.moveToFirst()) {
            db.execSQL("INSERT INTO users VALUES ('${editTextname.text}', '${editTextfirname.text}', '${editTextnumber.text}');")
        }
        query.close()
        db.close()

    }
}