package com.gautham.nbateamviewer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gautham.nbateamviewer.R
import com.gautham.nbateamviewer.models.Team
import com.gautham.nbateamviewer.viewholders.OnTeamSelectedListener
import com.gautham.nbateamviewer.viewholders.TeamsViewHolder

class TeamsAdapter(private var onTeamSelectedListener: OnTeamSelectedListener) :
    RecyclerView.Adapter<TeamsViewHolder>() {

    private var nbaTeams: List<Team> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false)
        return TeamsViewHolder(view, onTeamSelectedListener)
    }

    override fun getItemCount(): Int = nbaTeams.size

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bind(nbaTeams[position])
    }

    fun setTeams(teams: List<Team>) {
        this.nbaTeams = teams
        notifyDataSetChanged()
    }

    fun getTeam(position: Int): Team = nbaTeams[position]
}