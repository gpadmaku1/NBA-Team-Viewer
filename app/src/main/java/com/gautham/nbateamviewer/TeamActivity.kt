package com.gautham.nbateamviewer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gautham.nbateamviewer.models.Team

class TeamActivity : AppCompatActivity() {

    companion object {
        val TAG: String = TeamActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        if (intent.hasExtra("team")) {
            val team = intent.getParcelableExtra<Team>("team")
            Log.d(TAG, team.toString())
        }
    }
}
