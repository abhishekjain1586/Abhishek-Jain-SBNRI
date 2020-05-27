package com.sbnri.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sbnri.model.response.Repo
import com.sbnri.repository.RepoRepository

class MainActivityViewModel : ViewModel(), RepoRepository.OnRepositoryFetchListener {

    private lateinit var loader : MutableLiveData<Boolean>
    private lateinit var displayMessage : MutableLiveData<String>
    private var mRepositoryRepo = RepoRepository()
    private lateinit var mRepoLiveData : MutableLiveData<ArrayList<Repo>>
    private var mRepoLst = ArrayList<Repo>()

    private var mPageNoRepo = 1
    private var toLoadMore = false


    fun showProgressDialog() : MutableLiveData<Boolean> {
        if (!::loader.isInitialized) {
            loader = MutableLiveData<Boolean>()
        }
        return loader
    }

    fun showError() : MutableLiveData<String> {
        if (!::displayMessage.isInitialized) {
            displayMessage = MutableLiveData<String>()
        }
        return displayMessage
    }


    fun getRepo() : MutableLiveData<ArrayList<Repo>> {
        /*if (mRepoLiveData == null) {
            mRepoLiveData = MutableLiveData<ArrayList<Repo>>()
            mRepositoryRepo.setListener(this)
            fetchRepoData()
        }*/
        if (!::mRepoLiveData.isInitialized) {
            mRepoLiveData = MutableLiveData<ArrayList<Repo>>()
            mRepositoryRepo.setListener(this)
            fetchRepoData()
        }
        return mRepoLiveData
    }

    private fun fetchRepoData() {
        loader.value = true
        mRepositoryRepo.getRepos(mPageNoRepo)
    }

    fun toLoadMore() : Boolean {
        return toLoadMore
    }

    fun loadMoreRepo() {
        fetchRepoData()
    }

    override fun onSuccess(responseLst: ArrayList<Repo>?) {
        loader.value = false

        toLoadMore = false
        if (responseLst != null && responseLst.size > 0) {
            toLoadMore = true
            mPageNoRepo++
            mRepoLst.addAll(responseLst)
            mRepoLiveData.value = mRepoLst
        }
    }

    override fun onFailure(errorMsg: String) {
        loader.value = false
        if (mRepoLst.isEmpty()) {
            val tempLst = mRepositoryRepo.getRepoLstFromDb()
            if (tempLst != null && tempLst.isNotEmpty()) {
                mRepoLst.addAll(tempLst)
                mRepoLiveData.value = mRepoLst
            } else {
                displayMessage.value = errorMsg
            }
        }/* else {
            displayMessage.value = errorMsg
        }*/
    }
}