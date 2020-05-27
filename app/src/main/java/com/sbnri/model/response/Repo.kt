package com.sbnri.model.response

import com.google.gson.annotations.SerializedName

class Repo {

    @SerializedName("id")
    var id : Long? = null

    @SerializedName("name")
    var name : String? = null

    @SerializedName("description")
    var description : String? = null

    @SerializedName("open_issues_count")
    var open_issues_count : Int? = null

    @SerializedName("license")
    var license : License? = null

    @SerializedName("permissions")
    var permissions : Permissions? = null

}