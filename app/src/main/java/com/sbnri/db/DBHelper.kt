package com.sbnri.db

import androidx.room.Room
import com.sbnri.application.MyApp

object DBHelper {

    private var dbInstance_ : RepoDatabase? = null

    fun getInstance() : RepoDatabase {
        if (dbInstance_ == null) {
            dbInstance_ = Room.databaseBuilder(
                MyApp._INSTANCE.applicationContext,
                RepoDatabase::class.java,
                "repositories.db")
                .allowMainThreadQueries()
                .build()
        }
        return dbInstance_ as RepoDatabase
    }
}