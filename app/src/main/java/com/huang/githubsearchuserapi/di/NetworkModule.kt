package com.huang.githubsearchuserapi.di

import com.huang.githubsearchuserapi.BuildConfig
import com.huang.githubsearchuserapi.api.AppApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single { builder(BuildConfig.HOST).create(AppApi::class.java) }
}

private fun builder(url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(OkHttpClient())
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}