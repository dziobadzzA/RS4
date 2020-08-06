package com.example.cattaskapp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController

import android.widget.VideoView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.cattaskapp.CatViewModel
import com.example.cattaskapp.Channel
import com.example.cattaskapp.MainActivity
import com.example.cattaskapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CatAdapter(context: Context?) : RecyclerView.Adapter<CatViewHolder>() {

    private val items = mutableListOf<Channel>()
    private var contextMain: Context? = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_channel_r_s_s, null)
        return CatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(newItems: List<Channel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: CatViewHolder) {
        super.onViewAttachedToWindow(holder);
        var layoutPosition = holder.layoutPosition;
        if (layoutPosition == items.size - 1)
        {
            GlobalScope.launch (Dispatchers.Main)
            {
                newaddItems()
            }
        }
    }


    suspend fun newaddItems() {

        val _itemsnew = MutableLiveData<List<Channel>>()
        val itemsnew: LiveData<List<Channel>> = _itemsnew
        _itemsnew.value = MainActivity.CatApiImpl.getListOfCats()

        var buf: List<Channel> = itemsnew.value!!

        if (buf != null) {
            items.addAll(buf)
            notifyDataSetChanged()
        }

    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {

        val bufTitle = (items[position].getTitle() ?: "").split("|")

        val autorName = bufTitle[1]
        val titleName: String = bufTitle[0]
        val videoUrl = items[position].getUrl() ?: ""
        val bufTag:String? =  videoUrl + "|" + (items[position].getDescription() ?: "") + "|" + autorName + "|" + titleName

        var vidtest:Uri = Uri.parse(videoUrl)

        var mediaController: MediaController = MediaController(contextMain)

        holder.videoView.requestFocus(0)
        holder.videoView.setMediaController(mediaController)
        holder.videoView.setVideoURI(vidtest)
        mediaController.setAnchorView(holder.videoView)
        //holder.videoView.start()
        holder.textView2.text = autorName
        holder.textView2.tag = bufTag
        holder.textView3.text = titleName
        holder.textView3.tag = bufTag
    }

}


class  CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val videoView:VideoView = view.findViewById<VideoView>(R.id.videoView)
    val textView2:TextView = view.findViewById<TextView>(R.id.textView2)
    val textView3:TextView = view.findViewById<TextView>(R.id.textView3)

}



