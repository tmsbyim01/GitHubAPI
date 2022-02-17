package com.huang.githubsearchuserapi.ui.search.view

import android.view.View
import androidx.core.view.isVisible
import com.huang.githubsearchuserapi.ui.base.BaseViewHolderWithController
import kotlinx.android.synthetic.main.item_search_foot.view.*

class SearchFootViewHolder(itemView: View) :
    BaseViewHolderWithController<SearchInfo, SearchController>(itemView) {
    override fun onBind(data: SearchInfo, controller: SearchController) {
        if (data !is SearchFootInfo) return
        itemView.progressBar.isVisible = data.hasMore
        itemView.imageView.isVisible = !data.hasMore
    }
}