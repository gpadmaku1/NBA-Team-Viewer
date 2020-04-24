package com.gautham.nbateamviewer

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.airbnb.lottie.LottieAnimationView
import com.gautham.nbateamviewer.adapters.TeamsAdapter
import com.gautham.nbateamviewer.viewholders.OnTeamSelectedListener
import com.gautham.nbateamviewer.viewmodels.TeamsViewModel

class MainActivity : AppCompatActivity(), OnTeamSelectedListener {

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    @BindView(R.id.teams_rv)
    lateinit var teamsRecyclerView: RecyclerView

    @BindView(R.id.lottie_view)
    lateinit var lottieLoadingView: LottieAnimationView

    private val teamsAdapter = TeamsAdapter(this)
    private val teamsViewModel by lazy { TeamsViewModel.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        initRecyclerView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        if (hasNetworkAvailable()) {
            lottieLoadingView.visibility = View.VISIBLE
            teamsViewModel.fetchTeams()
            teamsViewModel.getTeams().observe(this, Observer { list ->
                teamsAdapter.setTeams(list)
                lottieLoadingView.visibility = View.GONE
            })
        } else {
            lottieLoadingView.visibility = View.GONE
            Toast.makeText(this, "No internet connection.", LENGTH_SHORT).show()
        }
    }

    private fun initRecyclerView() {
        teamsRecyclerView.apply {
            adapter = teamsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onTeamSelected(position: Int) {
        val team = teamsAdapter.getTeam(position)
        val intent = Intent(this, TeamActivity::class.java)
        intent.putExtra("team", team)
        startActivity(intent)
    }

    private fun hasNetworkAvailable(): Boolean {
        let {
            val service = Context.CONNECTIVITY_SERVICE
            val manager = it.getSystemService(service) as ConnectivityManager?
            val network = manager?.activeNetworkInfo
            Log.d(TAG, "hasNetworkAvailable: ${(network != null)}")
            return (network != null)
        }
    }
}
