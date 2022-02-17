package com.huang.githubsearchuserapi.di

import com.huang.githubsearchuserapi.ui.search.viewmodel.SearchRepository
import org.koin.dsl.module

val apiModule = module {
    single { SearchRepository(get()) }
}