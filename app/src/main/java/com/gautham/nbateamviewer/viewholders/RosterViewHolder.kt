package com.gautham.nbateamviewer.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.gautham.nbateamviewer.R
import com.gautham.nbateamviewer.models.Player

class RosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.player_name)
    lateinit var playerName: TextView

    @BindView(R.id.position_number)
    lateinit var positionNumber: TextView

    init {
        ButterKnife.bind(this, itemView)
    }

    fun bind(player: Player) {
        itemView.context.apply {
            playerName.text = getString(R.string.player_name, player.first_name, player.last_name)
            positionNumber.text = getString(R.string.player_position_number, player.position, player.number)
        }
    }
}