package com.sbnri.service

import com.sbnri.model.response.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface APIClient {

    // Top Rated Movies
    @GET("repos")
    fun getRepos(@QueryMap userDetailReq: Map<String, Int>) : Call<ArrayList<Repo>>

}