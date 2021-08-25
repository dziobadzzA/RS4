package com.example.myfirstapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.database.MentorDatabase
import com.example.myfirstapplication.model.Mentor


class DataAdapter(context: Context?): RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    private val mentor: MutableList<MentorDatabase>? = mutableListOf()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mentor = mentor?.get(position)
        holder.nameView.text = "Name: " +  mentor?.name ?: ""
        holder.firstnameView.text = "FirstName: " +mentor?.firstname ?: ""
        holder.numberView.text = "Phone: " + mentor?.number ?: ""
    }

    override fun getItemCount(): Int {
        return mentor?.size ?: 0
    }

    inner class ViewHolder internal constructor(view: View): RecyclerView.ViewHolder(view) {
        val nameView: TextView = (view.findViewById<View>(R.id.name) as TextView?)!!
        val firstnameView: TextView = (view.findViewById<View>(R.id.firstname) as TextView?)!!
        val numberView: TextView = (view.findViewById<View>(R.id.number) as TextView?)!!
    }

    fun addItems(item:MentorDatabase) {
        mentor?.add(item)
        notifyDataSetChanged()
    }

    fun deleteItems() = mentor?.clear()

    fun addItemsAll(items: List<MentorDatabase>) {
        mentor?.addAll(items)
        notifyDataSetChanged()
    }

    fun deleteItem(p:Int) {
        mentor?.removeAt(p)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): MentorDatabase? {
        return mentor?.get(position)
    }

}

interface SwipeDelete {
    fun deleteItem(position: Int)
}