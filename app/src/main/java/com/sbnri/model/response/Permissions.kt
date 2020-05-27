package com.sbnri.model.response

import com.google.gson.annotations.SerializedName

class Permissions {

    @SerializedName("admin")
    var admin : Boolean? = null

    @SerializedName("push")
    var push : Boolean? = null

    @SerializedName("pull")
    var pull : Boolean? = null
}