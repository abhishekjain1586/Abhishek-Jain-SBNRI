package com.sbnri.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sbnri.R

class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvName: TextView = itemView.findViewById(R.id.tv_name)
    var tvOpenIssuesCount: TextView = itemView.findViewById(R.id.tv_open_issues_count)
    var tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
    var tvLicense: TextView = itemView.findViewById(R.id.tv_license_value)
    var tvAdmin: TextView = itemView.findViewById(R.id.tv_admin)
    var tvPull: TextView = itemView.findViewById(R.id.tv_pull)
    var tvPush: TextView = itemView.findViewById(R.id.tv_push)

}