package dev.qori.deckiru.utils

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.qori.deckiru.R

class DeckViewHolder (view: View) : RecyclerView.ViewHolder(view){

        val tvDeckROwItemName: TextView = view.findViewById<TextView>(R.id.tvDeckRowItemName)
}