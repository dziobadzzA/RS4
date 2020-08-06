package com.example.cattaskapp

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.cattaskapp.adapter.CatAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

interface CatApi {
     suspend fun getListOfCats(): List<Channel>
     suspend fun setListOfCats(input: MutableList<Channel>)
}


@Suppress("RedundantOverride", "DEPRECATION")
class MainActivity : AppCompatActivity()  {

    private val itemAdapter = CatAdapter(this)
    private val catViewModel by viewModels<CatViewModel>()

    var channels:MutableList<Channel> = mutableListOf()

    object CatApiImpl {
        var output: MutableList<Channel> = mutableListOf()

        fun getListOfCats(): List<Channel> {
            return output
        }

        fun setListOfCats(input: MutableList<Channel>) {
            this.output = input
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeDisplay()

        recyclerView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        catViewModel.items.observe(this, Observer {
            it ?: return@Observer
            itemAdapter.addItems(it)
        })

    }


    override fun onResume() {

        homeDisplay()

        super.onResume()
    }

    fun homeDisplay()
    {

        var prefx = PreferenceManager.getDefaultSharedPreferences(this)
        var sortirload= prefx.getString("otborSourse", "XML")

        if (sortirload == "XML")
        {
            val downloadData = DownloadData()
            downloadData.execute("https://www.ted.com/themes/rss/id")
            channels = downloadData.get()
            CatApiImpl.setListOfCats(channels)
        }
        else
        {
            val text = resources.openRawResource(R.raw.data)
                    .bufferedReader().use { it.readText() }
            val downloadDataJSON = DownloadDataJSON()
            downloadDataJSON.execute(text)
            channels = downloadDataJSON.get()
            CatApiImpl.setListOfCats(channels)
        }

        var sortirTheme= prefx.getString("otbor", "Dark")

        if (sortirTheme == "Dark")
        {
            setTheme(R.style.AppTheme)
            var color:Int = R.color.colorPrimary
            ThemeUpdate(color)
        }
        else
        {
            setTheme(R.style.AppThemeLight)
            var color:Int = R.color.colorPrimaryDark
            ThemeUpdate(color)
        }


    }

    fun ThemeUpdate(color:Int)
    {
        // уведомление о смене темы
        // Меняем "системный UI" прямо на месте
        var typedValue: TypedValue = TypedValue()
        var theme:Resources.Theme = getTheme()

        val FloatBut:FloatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        theme.resolveAttribute(android.R.attr.windowBackground, typedValue, true)
        getWindow().getDecorView().setBackgroundColor(typedValue.data)

        theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
        getSupportActionBar()?.setBackgroundDrawable(ColorDrawable(typedValue.data))

        FloatBut.backgroundTintList = baseContext.resources.getColorStateList(color)

        theme.resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true)
        var window:Window = getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(typedValue.data)
    }


    fun filt(view: View)
    {
        var intent = Intent(this, filter::class.java)
        startActivity(intent)
    }


    fun clickVideo(view:View)
    {
        var intent = Intent(this, fragmentCat::class.java)
        intent.putExtra(fragmentCat.urlsave, view.tag.toString())
        startActivity(intent)
    }



    @Suppress("DEPRECATION")
    class DownloadData : AsyncTask<String?, Void?, MutableList<Channel>>() {

        override fun onPostExecute(s: MutableList<Channel>) {
            super.onPostExecute(s)
        }

        override fun doInBackground(vararg params: String?): MutableList<Channel> {
            var content: String? = null
            try {
                content = params[0]?.let { downloadXML(it) }
            } catch (ex: IOException) { }

            val parser = ChannelXmlParser()

            var outResult: MutableList<Channel> = mutableListOf()

            if (content != null && parser.parse(content))
            {
                outResult = parser.getChannel()
            }
            return outResult
        }

        @Throws(IOException::class)
        private fun downloadXML(urlPath: String): String?
        {
            val xmlResult = StringBuilder()
            var reader: BufferedReader? = null
            try
            {
                val url = URL(urlPath)
                val connection: HttpsURLConnection = url.openConnection() as HttpsURLConnection

                reader = BufferedReader(InputStreamReader(connection.inputStream))
                var line: String? = null
                while (reader.readLine().also { line = it } != null)
                {
                    var notabt = line.toString().split("\t")
                    for (i in 0..notabt.size - 1) {
                       if (notabt[i] != "") xmlResult.append(notabt[i])
                    }
                }
                return xmlResult.toString()
            }
            catch (e: IOException) { }
            finally { reader?.close() }
            return null
        }

    }

    @Suppress("DEPRECATION")
    class DownloadDataJSON() : AsyncTask<String?, Void?, MutableList<Channel>>() {

        override fun onPostExecute(result: MutableList<Channel>)
        {
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg params: String?):MutableList<Channel> {
            val generalJSON = JSONObject(params[0])
            val generalArrayItem = generalJSON.getJSONObject("channel").getJSONArray("item")

            var outResChannel: MutableList<Channel> = mutableListOf()

            for(i in 0..generalArrayItem.length() - 1)
            {
                var bufChannel: Channel = Channel()
                bufChannel?.setTitle((generalArrayItem[i] as JSONObject).getString("title"))
                bufChannel?.setDescription((generalArrayItem[i] as JSONObject).getString("description"))

                bufChannel?.setUrl((generalArrayItem[i] as JSONObject).getJSONObject("enclosure").getString("url"))

                outResChannel?.add(bufChannel)
            }

            return outResChannel
        }

    }


}


