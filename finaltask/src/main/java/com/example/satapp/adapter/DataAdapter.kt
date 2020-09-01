package com.example.satapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.satapp.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DataAdapter(context: Context?, sortirField: String) : RecyclerView.Adapter<ViewHolder>() {

    private val items = mutableListOf<SatModel>()
    private var sortirField = sortirField

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem():List<SatModel> { return items }

    fun addItems(newItems: List<SatModel>) {
        items.clear()
        items.addAll(sortirItem(newItems, sortirField))
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
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

        val _itemsnew = MutableLiveData<List<SatModel>>()
        val itemsnew: LiveData<List<SatModel>> = _itemsnew
        _itemsnew.value = SatApiImpl.getListOfSats()

        var buf: List<SatModel> = itemsnew.value!!

        if (buf != null) {
            items.addAll(sortirItem(buf,sortirField))
            notifyDataSetChanged()
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val mentor = items[position]
        holder.nameView.text = "Satellite " + mentor.getName()
        holder.numberView.text = "Position " + mentor.getPoint()

        holder.imageView.tag = mentor.getName()

        when(mentor.getImage())
        {
            "1" -> holder.imageView.setImageResource(R.drawable.intelsat)
            "2" -> holder.imageView.setImageResource(R.drawable.eutelsat)
            else -> holder.imageView.setImageResource(R.drawable.sat)
        }

    }

    fun sortirItem(it:List<SatModel>, sortirItem: String):List<SatModel>
    {

        var mapitem: MutableList<String> = mutableListOf()

        for(i in it.indices)
        {
            when(sortirItem)
            {
                "point" -> it[i].getPoint()?.let { it1 -> mapitem.add(it1) }
                else -> it[i].getName()?.let { it1 -> mapitem.add(it1) }
            }
        }

        mapitem.sort()

        var itItem: MutableList<SatModel> = mutableListOf()

        when(sortirItem)
        {
            "point" ->
            {
                for (j in 0 until mapitem.size)
                    for(i in it.indices)
                    {
                        if (it[i].getPoint() == mapitem[j])
                        {
                            itItem.add(it[i])
                            mapitem.removeAt(i)
                            break
                        }
                    }
            }
            else -> {
                while (mapitem.size > 0)
                    for(i in it.indices)
                    {
                        if (it[i].getName() == mapitem.first())
                        {
                            itItem.add(it[i])
                            mapitem.removeAt(0)
                            break
                        }
                    }
            }
        }

        return itItem
    }

}


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView = view.findViewById(R.id.imageView) as ImageView
    val nameView = view.findViewById<View>(R.id.name) as TextView
    val numberView = view.findViewById<View>(R.id.number) as TextView
}




