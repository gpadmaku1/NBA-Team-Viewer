package com.gautham.nbateamviewer

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
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

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    private val teamsAdapter = TeamsAdapter(this)
    private val teamsViewModel by lazy { TeamsViewModel.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        initRecyclerView()
        subscribeObservers()
        toolbar.overflowIcon =
            ContextCompat.getDrawable(applicationContext, R.drawable.ic_filter_list_black_24dp)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    private fun subscribeObservers() {
        if (hasNetworkAvailable()) {
            lottieLoadingView.visibility = View.VISIBLE
            teamsViewModel.fetchTeams()
            teamsViewModel.getTeams().observe(this, Observer { list ->
                val sortedList = list.sortedBy { it.full_name }
                teamsAdapter.setTeams(sortedList)
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
            return (network != null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.filter_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.sort_by_ascending -> {
                sortTeams(SortType.SORT_ASCENDING)
                true
            }
            R.id.sort_by_descending -> {
                sortTeams(SortType.SORT_DESCENDING)
                true
            }
            R.id.sort_by_wins -> {
                sortTeams(SortType.SORT_BY_WINS)
                true
            }
            R.id.sort_by_losses -> {
                sortTeams(SortType.SORT_BY_LOSSES)
                true
            }
            else -> {
                true
            }
        }
    }

    private fun sortTeams(sortType: SortType) {
        when (sortType) {
            SortType.SORT_ASCENDING -> {
                teamsAdapter.setTeams(teamsAdapter.getTeams().sortedBy { it.full_name })
            }
            SortType.SORT_DESCENDING -> {
                teamsAdapter.setTeams(teamsAdapter.getTeams().sortedBy { it.full_name }.reversed())
            }
            SortType.SORT_BY_WINS -> {
                teamsAdapter.setTeams(teamsAdapter.getTeams().sortedBy { it.wins }.reversed())
            }
            SortType.SORT_BY_LOSSES -> {
                teamsAdapter.setTeams(teamsAdapter.getTeams().sortedBy { it.losses }.reversed())
            }
        }
    }
}
