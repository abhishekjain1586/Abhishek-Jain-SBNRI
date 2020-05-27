package com.sbnri.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sbnri.utils.Constants

@Entity(tableName = "repo")
class RepoEntity {

    @ColumnInfo(name = "repoId")
    @PrimaryKey
    var repoId : Long? = null

    @ColumnInfo(name = "name")
    var name : String = Constants.EMPTY

    @ColumnInfo(name = "description")
    var description : String = Constants.EMPTY

    @ColumnInfo(name = "open_issues_count")
    var open_issues_count : Int? = null

    @ColumnInfo(name = "license_name")
    var license_name : String = Constants.EMPTY

    @ColumnInfo(name = "admin")
    var admin : Boolean = false

    @ColumnInfo(name = "pull")
    var pull : Boolean = false

    @ColumnInfo(name = "push")
    var push : Boolean = false
}