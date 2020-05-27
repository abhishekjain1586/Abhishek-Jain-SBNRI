package com.sbnri.db.dao

import androidx.room.*
import com.sbnri.db.entities.RepoEntity

@Dao
abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(repoEntity : RepoEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertAll(repoLst : List<RepoEntity>)

    @Delete
    abstract fun delete(repoEntity : RepoEntity)

    @Query("select * from repo")
    abstract fun getAllRepositories() : List<RepoEntity>?
}