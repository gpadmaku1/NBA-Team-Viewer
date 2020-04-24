package com.gautham.nbateamviewer.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.gautham.nbateamviewer.R
import com.gautham.nbateamviewer.models.Team

class TeamsViewHolder(itemView: View, onTeamSelectedListener: OnTeamSelectedListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    @BindView(R.id.team_name)
    lateinit var teamName: TextView

    @BindView(R.id.win_loss)
    lateinit var winLoss: TextView

    private var onTeamSelectedListener: OnTeamSelectedListener

    init {
        ButterKnife.bind(this, itemView)
        this.onTeamSelectedListener = onTeamSelectedListener
    }

    fun bind(team: Team) {
        teamName.text = team.full_name
        winLoss.text = itemView.context.getString(
            R.string.teams_win_loss_text, team.wins, team.losses
        )
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onTeamSelectedListener.onTeamSelected(adapterPosition)
    }
}

interface OnTeamSelectedListener {
    fun onTeamSelected(position: Int)
}