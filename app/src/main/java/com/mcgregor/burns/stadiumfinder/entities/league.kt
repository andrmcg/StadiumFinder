package com.mcgregor.burns.stadiumfinder.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "league_table")
class League {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int? = null

    @NonNull
    @ColumnInfo(name = "name")
    var name:String? = null

    @ColumnInfo(name = "logo")
    var logo:String = ""

    constructor(){}

    @Ignore
    constructor(name:String, logo:String){
        this.name = name
        this.logo = logo
    }

}