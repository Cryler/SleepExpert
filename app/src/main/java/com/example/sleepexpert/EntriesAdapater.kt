package com.example.sleepexpert

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntriesAdapater (val entryList: ArrayList<EntriesItems>) : RecyclerView.Adapter<EntriesAdapater.ViewHolder>(){

    override fun getItemCount(): Int {
      return entryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry: EntriesItems = entryList[position]

        holder?.textViewDate.text = entry.date
        holder?.textViewHours.text = entry.hours
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent?.context).inflate(R.layout.activity_entries_items, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textViewDate = itemView.findViewById<TextView>(R.id.viewEntryDate)
        val textViewHours = itemView.findViewById<TextView>(R.id.viewHours)
    }

}
