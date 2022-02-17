package com.huang.githubsearchuserapi.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huang.githubsearchuserapi.R
import com.huang.githubsearchuserapi.ui.search.view.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment.newInstance())
                .commitNow()
        }
    }
}