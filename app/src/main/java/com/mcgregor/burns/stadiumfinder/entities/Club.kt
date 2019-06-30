package com.mcgregor.burns.stadiumfinder.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "clubs_table")
class Club {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int? = null

    @NonNull
    @ColumnInfo(name = "teamname")
    var teamName: String? = null

    @ColumnInfo(name = "stadiumname")
    var stadiumName: String = ""

    @ColumnInfo(name="formed")
    var formed: String = ""

    @ColumnInfo(name = "league")
    var league: String = ""

    @ColumnInfo(name = "address")
    var address: String = ""

    @ColumnInfo(name = "zip")
    var zip: String = ""

    @ColumnInfo(name = "city")
    var city: String = ""

    @ColumnInfo(name = "logo")
    var logo: String = ""

    @ColumnInfo(name = "lat")
    var lat: Double = 0.0

    @ColumnInfo(name = "lon")
    var lon: Double = 0.0

    @ColumnInfo(name = "uri")
    var uri: String? = null

    constructor(){}

    @Ignore
    constructor(teamname:String, stadiumname: String, formed:String, league:String, address: String, zip:String,
                city:String,logo:String, lat:Double, lon:Double, uri: String){
        this.teamName = teamname
        this.stadiumName = stadiumname
        this.formed = formed
        this.address = address
        this.league = league
        this.zip = zip
        this.city = city
        this.logo = logo
        this.lat = lat
        this.lon = lon
        this.uri = uri
    }

}