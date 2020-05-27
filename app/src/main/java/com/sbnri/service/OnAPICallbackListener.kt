package com.sbnri.service

interface OnAPICallbackListener {

    fun onSuccess()

    fun onFailure()

    fun onException()
}