package com.example.sleepexpert

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntriesAdapter (private val entryList: ArrayList<EntryItem>) : RecyclerView.Adapter<EntriesAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
      return entryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry: EntryItem = entryList[position]

        holder.textViewDate.text = entry.date
        holder.textViewHours.text = entry.hours
        holder.itemView.tag = position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.activity_entry_items, parent, false)
        view.setOnClickListener {
            val context = view.context
            val intent = Intent(context, MorningEntryEdit::class.java)
            intent.putExtra(MorningEntryEdit.DOCUMENT_ID, it.tag.toString())
            context.startActivity(intent)
        }
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textViewDate: TextView = itemView.findViewById(R.id.viewEntryDate)
        val textViewHours: TextView = itemView.findViewById(R.id.viewHours)
    }

}
