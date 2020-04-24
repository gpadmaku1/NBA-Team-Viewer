package com.gautham.nbateamviewer.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import com.gautham.nbateamviewer.MainActivity
import com.gautham.nbateamviewer.models.Team
import com.gautham.nbateamviewer.utils.Constants
import com.gautham.nbateamviewer.utils.NetworkUtils
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamsViewModel : ViewModel() {
    companion object {
        fun get(activity: MainActivity) =
            ViewModelProviders.of(activity).get(TeamsViewModel::class.java)
    }

    private val nbaTeams: MutableLiveData<List<Team>> = MutableLiveData()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    fun getTeams(): MutableLiveData<List<Team>> {
        return nbaTeams
    }

    fun fetchTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = NetworkUtils.getRequest(Constants.API_URL)
            if (response != null) {
                val type = Types.newParameterizedType(List::class.java, Team::class.java)
                val adapter: JsonAdapter<List<Team>> = moshi.adapter(type)
                val teams = adapter.fromJson(response)
                launch(Dispatchers.Main) {
                    nbaTeams.value = teams
                }
            }
        }
    }
}