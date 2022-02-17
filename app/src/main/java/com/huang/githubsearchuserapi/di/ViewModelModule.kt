package com.huang.githubsearchuserapi.di

import com.huang.githubsearchuserapi.ui.search.viewmodel.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
}
