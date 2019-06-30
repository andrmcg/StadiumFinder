package com.mcgregor.burns.stadiumfinder.modules

import androidx.annotation.WorkerThread
import com.mcgregor.burns.stadiumfinder.entities.Club

class ClubRepository(private val dao:ClubDao) {

    val clubs = dao.selectAll()

    @WorkerThread
    suspend fun insert(club: Club){
        dao.insert(club)
    }

    @WorkerThread
    suspend fun deleteClub(club: Club){
        dao.delete(club)
    }

    @WorkerThread
    suspend fun selectLeagueClubs(name:String){
        dao.selectLeagueClubs(name)
    }

    @WorkerThread
    suspend fun get(club: Club){
        dao.select(club.teamName.toString())
    }

}