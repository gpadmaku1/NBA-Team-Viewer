package com.gautham.nbateamviewer

import com.gautham.nbateamviewer.models.Team
import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SortingUnitTests {

    @Test
    fun sortTeamsAscending() {
        val teams = ArrayList<Team>()
        teams.add(Team(60, 20, "Houston Rockets", ArrayList()))
        teams.add(Team(50, 30, "Golden State Warriors", ArrayList()))
        teams.add(Team(40, 40, "Toronto Raptors", ArrayList()))
        teams.add(Team(50, 30, "Milwaukee Bucks", ArrayList()))
        teams.add(Team(20, 60, "Brooklyn Nets", ArrayList()))
        val sortedList = teams.sortedBy { it.full_name }
        assertEquals("Brooklyn Nets", sortedList[0].full_name)
        assertEquals("Toronto Raptors", sortedList[sortedList.size - 1].full_name)
    }

    @Test
    fun sortTeamsDescending() {
        val teams = ArrayList<Team>()
        teams.add(Team(60, 20, "Houston Rockets", ArrayList()))
        teams.add(Team(50, 30, "Golden State Warriors", ArrayList()))
        teams.add(Team(40, 40, "Toronto Raptors", ArrayList()))
        teams.add(Team(50, 30, "Milwaukee Bucks", ArrayList()))
        teams.add(Team(20, 60, "Brooklyn Nets", ArrayList()))
        val sortedList = teams.sortedBy { it.full_name }.reversed()
        assertEquals("Toronto Raptors", sortedList[0].full_name)
        assertEquals("Brooklyn Nets", sortedList[sortedList.size - 1].full_name)
    }

    @Test
    fun sortTeamsByMostWins() {
        val teams = ArrayList<Team>()
        teams.add(Team(50, 30, "Golden State Warriors", ArrayList()))
        teams.add(Team(40, 40, "Toronto Raptors", ArrayList()))
        teams.add(Team(50, 30, "Milwaukee Bucks", ArrayList()))
        teams.add(Team(60, 20, "Houston Rockets", ArrayList()))
        teams.add(Team(20, 60, "Brooklyn Nets", ArrayList()))
        teams.add(Team(10, 70, "Philadelphia 76ers", ArrayList()))
        val sortedList = teams.sortedBy { it.wins }.reversed()
        assertEquals("Houston Rockets", sortedList[0].full_name)
        assertEquals("Philadelphia 76ers", sortedList[sortedList.size - 1].full_name)
    }

    @Test
    fun sortTeamsByMostLosses() {
        val teams = ArrayList<Team>()
        teams.add(Team(50, 30, "Milwaukee Bucks", ArrayList()))
        teams.add(Team(20, 60, "Brooklyn Nets", ArrayList()))
        teams.add(Team(60, 20, "Houston Rockets", ArrayList()))
        teams.add(Team(50, 30, "Golden State Warriors", ArrayList()))
        teams.add(Team(40, 40, "Toronto Raptors", ArrayList()))
        val sortedList = teams.sortedBy { it.losses }.reversed()
        assertEquals("Brooklyn Nets", sortedList[0].full_name)
        assertEquals("Houston Rockets", sortedList[sortedList.size - 1].full_name)
    }
}
