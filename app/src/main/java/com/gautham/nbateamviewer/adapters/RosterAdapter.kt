package com.gautham.nbateamviewer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gautham.nbateamviewer.R
import com.gautham.nbateamviewer.models.Player
import com.gautham.nbateamviewer.viewholders.RosterViewHolder

class RosterAdapter(private val players: List<Player>) : RecyclerView.Adapter<RosterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RosterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.roster_item,
            parent,
            false
        )
        return RosterViewHolder(view)
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: RosterViewHolder, position: Int) {
        holder.bind(players[position])
    }
}