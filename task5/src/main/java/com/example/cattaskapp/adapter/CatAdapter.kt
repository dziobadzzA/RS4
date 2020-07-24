package com.example.cattaskapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cattaskapp.CatApiImpl
import com.example.cattaskapp.R
import com.example.cattaskapp.data.Cat

import kotlinx.android.synthetic.main.layout_item.view.*

class CatAdapter : RecyclerView.Adapter<CatViewHolder>() {

    private val items = mutableListOf<Cat>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, null)
        return CatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val fileName = items[position].iddfr ?: ""
        val imageUrl = items[position].urldfr ?: ""
        holder.bind(fileName, imageUrl)
    }

    fun addItems(newItems: List<Cat>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: CatViewHolder) {
        super.onViewAttachedToWindow(holder);
        var layoutPosition = holder.layoutPosition;
        if (layoutPosition == items.size - 1)
        {
          //newaddItems()
        }
    }

     suspend fun newaddItems() {
        items.addAll(CatApiImpl.updateListOfCats())
        notifyDataSetChanged()
    }

}

class  CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val textView = view.findViewById<TextView>(R.id.textView)
    private val imageView = view.findViewById<ImageView>(R.id.imageView)

    fun bind(filmName: String, imageUrl: String) {
        textView.text = filmName
        imageView.load(imageUrl)
        imageView.tag = imageUrl
    }
}

