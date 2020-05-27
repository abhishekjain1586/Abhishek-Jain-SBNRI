package com.sbnri.repository

import com.eros.moviesdb.utils.NetworkUtil
import com.google.gson.Gson
import com.sbnri.R
import com.sbnri.application.MyApp
import com.sbnri.db.DBHelper
import com.sbnri.db.entities.RepoEntity
import com.sbnri.model.response.License
import com.sbnri.model.response.Permissions
import com.sbnri.model.response.Repo
import com.sbnri.model.response.RepoError
import com.sbnri.service.ServiceHelper
import com.sbnri.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val PER_PAGE = 10

class RepoRepository {
    private var mListener : OnRepositoryFetchListener? = null

    interface OnRepositoryFetchListener {
        fun onSuccess(repoLst : ArrayList<Repo>?)
        fun onFailure(errorMsg : String)
    }

    fun setListener(listner : OnRepositoryFetchListener) {
        mListener = listner
    }

    fun getRepos(page : Int) {
        if (NetworkUtil.isNetworkConnected()) {
            val queryParams = HashMap<String, Int>()
            queryParams.put("page", page)
            queryParams.put("per_page", PER_PAGE)

            var callback = ServiceHelper.getRetrofitClient().getRepos(queryParams)
            callback.enqueue(object : Callback<ArrayList<Repo>> {
                override fun onResponse(call: Call<ArrayList<Repo>>, response: Response<ArrayList<Repo>>) {
                    if (response.isSuccessful && response.body() != null) {
                        updateRepoToDb(response.body() as ArrayList<Repo>)
                        mListener?.onSuccess(response.body())
                    } else {
                        var errMsg = MyApp._INSTANCE.resources.getString(R.string.error_network)
                        response.errorBody()?.let {
                            it-> it.string()?.let {
                            it1 -> if (it1.contains("message")) {
                            val errObj = Gson().fromJson(it1, RepoError::class.java)
                            errObj.message?.let {
                                errMsg = it
                            }
                        }
                        }
                        }

                        mListener?.onFailure(errMsg)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Repo>>, t: Throwable) {
                    mListener?.onFailure(MyApp._INSTANCE.resources.getString(R.string.server_error))
                    /*getRepoLstFromDb()?.let {
                        mListener?.onSuccess(it)
                    } ?: mListener?.onFailure(MyApp._INSTANCE.resources.getString(R.string.server_error))*/
                }
            })
        } else {
            mListener?.onFailure(MyApp._INSTANCE.resources.getString(R.string.network_error))
            /*getRepoLstFromDb()?.let {
                mListener?.onSuccess(it)
            } ?: mListener?.onFailure(MyApp._INSTANCE.resources.getString(R.string.network_error))*/
        }
    }

    fun getRepoLstFromDb(): ArrayList<Repo>? {
        val repoEntityLst = DBHelper.getInstance().getRepoDao().getAllRepositories()

        if (repoEntityLst != null && repoEntityLst.isNotEmpty()) {
            val repoLst = ArrayList<Repo>()
            for (obj in repoEntityLst) {
                val repoObj = Repo()
                repoObj.id = obj.repoId
                repoObj.name = obj.name
                repoObj.description = obj.description
                repoObj.open_issues_count = obj.open_issues_count

                val license = License()
                license.name = obj.license_name
                repoObj.license = license

                val permission = Permissions()
                permission.admin = obj.admin
                permission.push = obj.push
                permission.pull = obj.pull
                repoObj.permissions = permission

                repoLst.add(repoObj)
            }
            return repoLst
        }

        return null
    }

    private fun updateRepoToDb(repoLst: ArrayList<Repo>) {
        if (repoLst.isNotEmpty()) {
            val repoEntityLst = ArrayList<RepoEntity>()
            for (obj in repoLst) {
                val repoEntityObj = RepoEntity()
                repoEntityObj.repoId = obj.id
                repoEntityObj.name = obj.name ?: Constants.EMPTY
                repoEntityObj.description = obj.description ?: Constants.EMPTY
                repoEntityObj.open_issues_count = obj.open_issues_count
                repoEntityObj.license_name = obj.license?.name ?: Constants.EMPTY
                repoEntityObj.admin = obj.permissions?.admin ?: false
                repoEntityObj.pull = obj.permissions?.pull ?: false
                repoEntityObj.push = obj.permissions?.push ?: false

                repoEntityLst.add(repoEntityObj)
            }
            DBHelper.getInstance().getRepoDao().insertAll(repoEntityLst)
        }
    }
}