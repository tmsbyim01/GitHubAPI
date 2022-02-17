package com.huang.githubsearchuserapi.ui.search.view

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huang.githubsearchuserapi.R
import com.huang.githubsearchuserapi.extension.observe
import com.huang.githubsearchuserapi.ui.base.BaseFragment
import com.huang.githubsearchuserapi.ui.search.view.SearchInfo.Companion.FOOT
import com.huang.githubsearchuserapi.ui.search.viewmodel.SearchViewModel
import com.huang.githubsearchuserapi.ui.search.viewmodel.SearchViewModel.State.Loading
import com.huang.githubsearchuserapi.ui.search.viewmodel.SearchViewModel.State.Result
import com.huang.githubsearchuserapi.ui.search.viewmodel.SearchViewModel.State.Error
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment(), SearchController {
    private val viewModel by viewModel<SearchViewModel>()
    private val searchAdapter = SearchAdapter(this)

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun getLayoutId() = R.layout.fragment_search

    override fun initView() {
        val gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (searchAdapter.getItemViewType(position) == FOOT) gridLayoutManager.spanCount else 1
            }
        }
        recyclerView.apply {
            adapter = searchAdapter
            layoutManager = gridLayoutManager
            itemAnimator = null
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                private var lastVisibleItem = 0

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (lastVisibleItem + 1 == searchAdapter.itemCount) {
                            viewModel.getUsers(textInputLayout.editText?.text.toString(), false)
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()
                }
            })
        }

        textInputEditText.setOnEditorActionListener { textView, id, _ ->
            if (EditorInfo.IME_ACTION_DONE == id || EditorInfo.IME_ACTION_NEXT == id) {
                hideKeyboard()
                viewModel.getUsers(textView.text.toString())
            }
            false
        }
    }

    override fun initObserver() {
        observe(viewModel.stateLiveData) {
            when(it) {
                is Loading -> {
                    includeLoading.isVisible = it.isInit
                    includeError.isVisible = false
                    includeNoResultFound.isVisible = false
                    recyclerView.isVisible = true
                }
                is Result -> {
                    recyclerView.isVisible = !it.isNotFound
                    includeNoResultFound.isVisible = it.isNotFound
                    includeLoading.isVisible = false
                    includeError.isVisible = false
                }
                is Error -> {
                    includeError.isVisible = true
                    includeError.findViewById<TextView>(R.id.textView).text = it.message
                    includeLoading.isVisible = false
                    includeNoResultFound.isVisible = false
                    recyclerView.isVisible = false
                }
            }
        }

        observe(viewModel.listSearchInfoLiveData) {
            searchAdapter.submitList(it.toList())
        }
    }

    override fun onItemClick(searchInfo: SearchInfo) {}
}