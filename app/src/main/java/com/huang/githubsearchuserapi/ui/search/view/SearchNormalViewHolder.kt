package com.huang.githubsearchuserapi.ui.search.view

import android.view.View
import com.huang.githubsearchuserapi.extension.setImageUrl
import com.huang.githubsearchuserapi.ui.base.BaseViewHolderWithController
import kotlinx.android.synthetic.main.item_search_normal.view.*

class SearchNormalViewHolder(itemView: View) :
    BaseViewHolderWithController<SearchInfo, SearchController>(itemView) {
    override fun onBind(data: SearchInfo, controller: SearchController) {
        if (data !is SearchNormalInfo) return
        itemView.imageView.setImageUrl(data.avatarUrl)
        itemView.textView.text = data.login
        itemView.setOnClickListener { controller.onItemClick(data) }
    }
}