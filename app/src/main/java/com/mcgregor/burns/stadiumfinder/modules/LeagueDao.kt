package com.mcgregor.burns.stadiumfinder.modules

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mcgregor.burns.stadiumfinder.entities.League

@Dao
interface LeagueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLeague(league: League)

    @Query("select * from league_table where name like :name")
    fun selectLeague(name:String): League

    @Query("select * from league_table")
    fun selectAllLeagues(): LiveData<List<League>>

    @Query("select name from league_table")
    fun seectLeagueNames(): LiveData<List<String>>

    @Query("delete from league_table")
    fun deleteAllLeagues()

}