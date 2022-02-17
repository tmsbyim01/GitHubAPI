package com.huang.githubsearchuserapi.api

import com.huang.githubsearchuserapi.model.UsersModel
import com.huang.githubsearchuserapi.ui.search.viewmodel.SearchViewModel.Companion.PER_PAGE
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {
    @GET("search/users")
    fun getUsers(
        @Query("q") q: String,
        @Query("per_page") perPage: Int = PER_PAGE,
        @Query("page") page: Int
    ): Single<UsersModel>
}