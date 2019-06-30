package com.mcgregor.burns.stadiumfinder.modules

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mcgregor.burns.stadiumfinder.entities.Club
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ClubViewModel(application:Application): AndroidViewModel(application) {

    private val repository:ClubRepository
    val clubs:LiveData<List<Club>>

    private val parentJob = Job()
    private val coroutineContext:CoroutineContext
    get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)


    init {
        val dao = ClubDatabase.getDatabase(application,scope).dao()
        repository = ClubRepository(dao)
        clubs = repository.clubs
    }

    fun selectLeagueClubs(name:String) = scope.launch(Dispatchers.IO){
        repository.selectLeagueClubs(name)
    }

    fun insertClub(club: Club) = scope.launch(Dispatchers.IO){
        repository.insert(club)
    }

    fun selectClub(club: Club) = scope.launch(Dispatchers.IO){
        repository.get(club)
    }

    fun deleteClub(club: Club) = scope.launch(Dispatchers.IO){
        repository.deleteClub(club)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}