package com.gautham.nbateamviewer

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.gautham.nbateamviewer.adapters.RosterAdapter
import com.gautham.nbateamviewer.models.Team

class TeamActivity : AppCompatActivity() {

    companion object {
        val TAG: String = TeamActivity::class.java.simpleName
    }

    @BindView(R.id.team_name)
    lateinit var teamName: TextView

    @BindView(R.id.win_loss)
    lateinit var winLoss: TextView

    @BindView(R.id.team_roster_rv)
    lateinit var teamRosterRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        ButterKnife.bind(this)

        if (intent.hasExtra("team")) {
            val team = intent.getParcelableExtra<Team>("team")
            Log.d(TAG, team.players.toString())
            Log.d(TAG, team.players.size.toString())
            displayTeamData(team)
        }
    }

    private fun displayTeamData(team: Team) {
        team.apply {
            teamName.text = full_name
            winLoss.text = getString(R.string.teams_win_loss_detailed_text, wins, losses)
            teamRosterRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@TeamActivity)
                adapter = RosterAdapter(players)
            }
        }
    }
}
