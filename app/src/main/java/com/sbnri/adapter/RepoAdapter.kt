package com.sbnri.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sbnri.R
import com.sbnri.model.response.Repo
import com.sbnri.utils.Constants
import com.sbnri.viewholder.RepoViewHolder

class RepoAdapter(context : Context) : RecyclerView.Adapter<RepoViewHolder>() {

    private val mRepoLst = ArrayList<Repo>()
    private val mContext = context

    fun setData(repoLst : ArrayList<Repo>) {
        this.mRepoLst.clear()
        this.mRepoLst.addAll(repoLst)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view : View = LayoutInflater.from(mContext).inflate(R.layout.item_repo, parent, false)
        return RepoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mRepoLst.size
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repoData : Repo = mRepoLst.get(position);

        holder.tvName.text = repoData.name ?: Constants.EMPTY

        holder.tvOpenIssuesCount.text = repoData.open_issues_count?.let {
            ""+it
        } ?: Constants.EMPTY

        holder.tvDesc.text = repoData.description ?: Constants.EMPTY

        holder.tvLicense.text = repoData.license?.let {
            it.name
        } ?: Constants.EMPTY

        var adminBgColor: Int = mContext.resources.getColor(android.R.color.holo_red_dark, null)
        var pullBgColor: Int = mContext.resources.getColor(android.R.color.holo_red_dark, null)
        var pushBgColor: Int = mContext.resources.getColor(android.R.color.holo_red_dark, null)

        repoData.permissions?.let {
            it -> it.admin?.let {
            it1 -> if (it1) {
            adminBgColor = mContext.resources.getColor(android.R.color.holo_green_dark, null)
        }
        }
        }
        holder.tvAdmin.setBackgroundColor(adminBgColor)

        repoData.permissions?.let {
                it -> it.pull?.let {
                it1 -> if (it1) {
            pullBgColor = mContext.resources.getColor(android.R.color.holo_green_dark, null)
        }
        }
        }
        holder.tvPull.setBackgroundColor(pullBgColor)

        repoData.permissions?.let {
                it -> it.push?.let {
                it1 -> if (it1) {
            pushBgColor = mContext.resources.getColor(android.R.color.holo_green_dark, null)
        }
        }
        }
        holder.tvPush.setBackgroundColor(pushBgColor)
    }

}