package com.mcgregor.burns.stadiumfinder.modules

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mcgregor.burns.stadiumfinder.entities.League
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LeagueViewModel(application: Application): AndroidViewModel(application) {

    private val repository:LeagueRepository
    val leagues: LiveData<List<League>>
    val leagueNames: LiveData<List<String>>

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val dao = ClubDatabase.getDatabase(application,scope).leagueDao()
        repository = LeagueRepository(dao)
        leagues = repository.leagues
        leagueNames = repository.leagueNames
    }

    fun insertLeague(league: League) = scope.launch(Dispatchers.IO){
        repository.insertLeague(league)
    }

    fun selectLEague(league: League) = scope.launch(Dispatchers.IO){
        repository.getLeague(league)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}