package com.huang.githubsearchuserapi.ui.search.viewmodel

import com.huang.githubsearchuserapi.api.AppApi
import com.huang.githubsearchuserapi.model.UsersModel
import io.reactivex.Flowable

class SearchRepository(
    private val appApi: AppApi
) {
    fun getUsers(q: String, page: Int): Flowable<UsersModel> = appApi.getUsers(q = q, page = page).toFlowable()
}