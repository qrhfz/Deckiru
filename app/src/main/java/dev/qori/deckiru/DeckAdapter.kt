package dev.qori.deckiru

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.qori.deckiru.model.Deck

class DeckAdapter(val context: Context, val items: ArrayList<Deck>) : RecyclerView.Adapter<DeckAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.deck_list_row,
                        parent,
                        false
                )
        )
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvDeckROwItemName = view.findViewById<TextView>(R.id.tvDeckRowItemName)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvDeckROwItemName.text = item.name
    }

    override fun getItemCount(): Int {
        return items.size
    }

}