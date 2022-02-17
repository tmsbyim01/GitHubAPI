package com.huang.githubsearchuserapi.ui.search.viewmodel

import androidx.lifecycle.MutableLiveData
import com.huang.githubsearchuserapi.ui.base.BaseViewModel
import com.huang.githubsearchuserapi.ui.search.view.SearchFootInfo
import com.huang.githubsearchuserapi.ui.search.view.SearchInfo
import com.huang.githubsearchuserapi.ui.search.view.SearchNormalInfo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(
    private val searchRepository: SearchRepository
) : BaseViewModel() {
    var listSearchInfoLiveData = MutableLiveData<MutableList<SearchInfo>>()
    var stateLiveData = MutableLiveData<State>()

    private var page = 1
    private var totalCount = 0

    companion object {
        const val MAX_COUNT = 1000
        const val PER_PAGE = 100
    }

    fun getUsers(q: String, isInit: Boolean = true) {
        if (!isInit &&
            (listSearchInfoLiveData.value?.size ?: 0 >= totalCount || listSearchInfoLiveData.value?.size ?: 0 >= MAX_COUNT)
        ) {
            return
        }
        page = when {
            isInit -> 1
            else -> page + 1
        }
        disposable.add(
            Flowable.fromCallable { stateLiveData.value = State.Loading(isInit) }
                .observeOn(Schedulers.io())
                .flatMap { searchRepository.getUsers(q, page) }
                .map { totalCount = (it.total_count ?: 0) ; it}
                .concatMap { Flowable.fromIterable(it.items) }
                .map { SearchNormalInfo.from(it) }
                .toList()
                .observeOn(Schedulers.computation())
                .map { concatDataList(it, isInit) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    stateLiveData.value = State.Result(it.first() is SearchFootInfo)
                    listSearchInfoLiveData.value = it
                },{
                    stateLiveData.value = State.Error(it.message)
                })
        )
    }

    private fun concatDataList(
        listData: MutableList<SearchInfo>,
        isInit: Boolean
    ): MutableList<SearchInfo> {
        val list = listSearchInfoLiveData.value
        return if (!isInit && list != null) {
            list.apply {
                removeLast()
                addAll(listData)
                add(SearchFootInfo.from(size < totalCount && size < MAX_COUNT))
            }
        } else {
            listData.add(SearchFootInfo.from(totalCount > 100)); listData
        }
    }

    sealed class State {
        data class Error(var message: String?): State()
        data class Loading(var isInit: Boolean): State()
        data class Result(var isNotFound: Boolean): State()
    }
}