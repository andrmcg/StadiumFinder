package com.mcgregor.burns.stadiumfinder.modules

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mcgregor.burns.stadiumfinder.entities.Club

@Dao
interface ClubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(club: Club)

    @Query("select * from clubs_table where teamname like :name")
    fun select(name:String): Club

    @Query("select * from clubs_table where league like :name")
    fun selectLeagueClubs(name:String):LiveData<List<Club>>

    @Query("select * from clubs_table")
    fun selectAll(): LiveData<List<Club>>

    @Delete
    fun delete(club: Club)

    @Query("delete from clubs_table")
    fun deleteAll()

}