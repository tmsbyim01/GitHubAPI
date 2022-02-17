package com.huang.githubsearchuserapi.ui.search.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.huang.githubsearchuserapi.R
import com.huang.githubsearchuserapi.model.UsersModel
import com.huang.githubsearchuserapi.ui.base.BaseViewHolderWithController
import com.huang.githubsearchuserapi.ui.base.Controller
import com.huang.githubsearchuserapi.ui.search.view.SearchInfo.Companion.NORMAL

class SearchAdapter(
    private val controller: SearchController
) : ListAdapter<SearchInfo, BaseViewHolderWithController<SearchInfo, SearchController>>(
    diffSearch
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolderWithController<SearchInfo, SearchController> {
        return when (viewType) {
            NORMAL -> {
                SearchNormalViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_search_normal,
                        parent,
                        false
                    )
                )
            }
            else -> {
                SearchFootViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_search_foot,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolderWithController<SearchInfo, SearchController>,
        position: Int
    ) {
        holder.onBind(getItem(position), controller)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }
}

abstract class SearchInfo {
    abstract val viewType: Int

    companion object {
        const val NORMAL = 0
        const val FOOT = 1
    }
}

data class SearchNormalInfo(
    override val viewType: Int = NORMAL,
    val id: Int,
    val login: String,
    val avatarUrl: String
): SearchInfo() {
    companion object {
        fun from(
            item: UsersModel.Item
        ): SearchInfo {
            return SearchNormalInfo(
                id = item.id ?: 0,
                login = item.login ?: "",
                avatarUrl = item.avatar_url ?: ""
            )
        }
    }
}

data class SearchFootInfo(
    override val viewType: Int = FOOT,
    var hasMore: Boolean
): SearchInfo() {
    companion object {
        fun from(
            hasMore: Boolean
        ): SearchInfo {
            return SearchFootInfo(hasMore = hasMore)
        }
    }
}

val diffSearch = object : DiffUtil.ItemCallback<SearchInfo>() {
    override fun areContentsTheSame(
        oldItem: SearchInfo,
        newItem: SearchInfo
    ): Boolean {
        return if (oldItem is SearchNormalInfo && newItem is SearchNormalInfo) {
            oldItem == newItem
        } else {
            true
        }
    }

    override fun areItemsTheSame(oldItem: SearchInfo, newItem: SearchInfo): Boolean {
        return if (oldItem is SearchNormalInfo && newItem is SearchNormalInfo) {
            oldItem.id == newItem.id
        } else {
            true
        }
    }
}

interface SearchController : Controller {
    fun onItemClick(searchInfo: SearchInfo)
}