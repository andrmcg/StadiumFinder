package com.mcgregor.burns.stadiumfinder.modules

import android.content.Context
import android.database.sqlite.SQLiteException
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mcgregor.burns.stadiumfinder.entities.Club
import com.mcgregor.burns.stadiumfinder.entities.League
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

@Database(entities = [Club::class, League::class], version = 1, exportSchema = false)
abstract class ClubDatabase : RoomDatabase() {

    abstract fun dao(): ClubDao
    abstract fun leagueDao(): LeagueDao

    companion object {
        @Volatile
        private var INSTANCE: ClubDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ClubDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance =
                    Room.databaseBuilder(context.applicationContext, ClubDatabase::class.java, "Club_database")
                        .addCallback(ClubDatabaseCallback(scope, context))
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }


    private class ClubDatabaseCallback(private val scope: CoroutineScope, context: Context) : RoomDatabase.Callback() {
        var ctx = context

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            try {
                if (db.query("select name from league_table").count <= 0) {
                    INSTANCE?.let { database ->
                        scope.launch(Dispatchers.IO) {
                            populateDatabase(database.dao(), database.leagueDao(), ctx)
                        }
                    }
                }
            }
            catch (e: SQLiteException){}
            finally {

            }
        }


        private fun populateDatabase(dao: ClubDao, leagueDao: LeagueDao, context: Context) {

                dao.deleteAll()
                leagueDao.deleteAllLeagues()

                val manager = context.resources.assets

                val parser = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                val inputStream = manager.open("clubs.xml")
                val document = parser.parse(inputStream)

                val clubs = document.documentElement.getElementsByTagName("club")

                val docSize = clubs.length
                for (i in 0..docSize - 1) {

                    val element = clubs.item(i) as Element
                    val teamname = element.getElementsByTagName("teamName").item(0).textContent
                    val stadiumname = element.getElementsByTagName("stadiumName").item(0).textContent
                    val formed = element.getElementsByTagName("formed").item(0).textContent
                    val league = element.getElementsByTagName("league").item(0).textContent
                    val address = element.getElementsByTagName("address").item(0).textContent
                    val zip = element.getElementsByTagName("postCode").item(0).textContent
                    val city = element.getElementsByTagName("city").item(0).textContent
                    val logo = element.getElementsByTagName("largelogo").item(0).textContent
                    val lat = element.getElementsByTagName("coord").item(0).attributes.getNamedItem("lat")
                        .textContent.toDouble()
                    val lon = element.getElementsByTagName("coord").item(0).attributes.getNamedItem("lon")
                        .textContent.toDouble()
                    val uri = element.getElementsByTagName("website").item(0).textContent
                    val club = Club(teamname, stadiumname, formed, league, address, zip, city, logo, lat, lon, uri)
                    dao.insert(club)
                }

                var leagues = document.documentElement.getElementsByTagName("league")
                var names = mutableListOf<String>()
                for (i in 0..leagues.length - 1) {
                    var element = leagues.item(i) as Element
                    if (!names.contains(element.textContent)) {
                        names.add(element.textContent)
                        var logo = element.attributes.getNamedItem("logo").textContent
                        var league = League(element.textContent, logo)
                        leagueDao.insertLeague(league)
                    }
                }
        }

    }

}