package com.sbnri.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sbnri.db.dao.RepoDao
import com.sbnri.db.entities.RepoEntity

@Database(entities = arrayOf(RepoEntity::class), version = 1, exportSchema = false)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun getRepoDao() : RepoDao

}