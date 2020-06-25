package com.example.myfirstapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


internal class DataAdapter(context: Context?, private val mentor: List<Mentor>): RecyclerView.Adapter<DataAdapter.ViewHolder>()
{
    private val inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val mentor = mentor[position]
        holder.nameView.setText(mentor.getName())
        holder.firnameView.setText(mentor.getFirname())
        holder.numberView.setText(mentor.getNumber())
    }

    override fun getItemCount(): Int {
        return mentor.size
    }

    inner class ViewHolder internal constructor(view: View): RecyclerView.ViewHolder(view)
    {
        val nameView: TextView
        val firnameView: TextView
        val numberView: TextView

        init
        {
            nameView = view.findViewById<View>(R.id.name) as TextView
            firnameView = view.findViewById<View>(R.id.firname) as TextView
            numberView = view.findViewById<View>(R.id.number) as TextView
        }
    }

    init { inflater = LayoutInflater.from(context) }
}
