package com.sbnri.view.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sbnri.R
import com.sbnri.adapter.RepoAdapter
import com.sbnri.model.response.Repo
import com.sbnri.viewmodel.MainActivityViewModel

class MainActivity : BaseActivity() {

    private lateinit var mRvRepo : RecyclerView

    private lateinit var viewModel : MainActivityViewModel
    private lateinit var mLinearLayoutManager : LinearLayoutManager
    private lateinit var mAdapter : RepoAdapter
    private var mRepoLst = ArrayList<Repo>()

    private var mToLoadMore = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(getString(R.string.repositories))

        initViews()
    }

    private fun initViews() {
        mRvRepo = findViewById(R.id.rv_repo)

        mLinearLayoutManager = LinearLayoutManager(this);
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvRepo.layoutManager = mLinearLayoutManager;
        mAdapter = RepoAdapter(this)
        mAdapter.setData(mRepoLst)
        mRvRepo.adapter = mAdapter

        initListeners()
        initViewModel()
    }

    private fun initListeners() {
        mRvRepo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount: Int = mLinearLayoutManager.childCount
                val totalItemCount: Int = mLinearLayoutManager.itemCount
                val firstVisibleItemPosition: Int = mLinearLayoutManager.findFirstVisibleItemPosition()

                if (mToLoadMore) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        mToLoadMore = false;
                        viewModel.loadMoreRepo()
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.showProgressDialog().observe(this, object : Observer<Boolean> {
            override fun onChanged(status: Boolean) {
                if (status) {
                    showProgressDialog()
                } else {
                    dismissProgressDialog()
                }
            }

        })

        viewModel.showError().observe(this, object : Observer<String> {
            override fun onChanged(errorMesg: String) {
                showDialog("Error", errorMesg)
            }

        })

        viewModel.getRepo().observe(this, object : Observer<ArrayList<Repo>> {
            override fun onChanged(response: ArrayList<Repo>?) {
                setData(response)
            }
        })
    }

    private fun setData(response: ArrayList<Repo>?) {
        mToLoadMore = viewModel.toLoadMore()
        if (response != null && response.size > 0) {
            mRepoLst.apply {
                clear()
                addAll(response)
            }
            mAdapter.setData(mRepoLst)
            mAdapter.notifyDataSetChanged()
        } else {

        }
    }

}
