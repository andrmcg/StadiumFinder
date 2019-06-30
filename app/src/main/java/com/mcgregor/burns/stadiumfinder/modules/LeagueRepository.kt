package com.mcgregor.burns.stadiumfinder.modules

import androidx.annotation.WorkerThread
import com.mcgregor.burns.stadiumfinder.entities.League

class LeagueRepository(private val dao:LeagueDao) {

    val leagues = dao.selectAllLeagues()

    val leagueNames = dao.seectLeagueNames()

    @WorkerThread
    suspend fun insertLeague(league: League){
        dao.insertLeague(league)
    }

    @WorkerThread
    suspend fun getLeague(league: League){
        dao.selectLeague(league.name.toString())
    }

}