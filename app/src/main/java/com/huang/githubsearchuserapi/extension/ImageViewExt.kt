package com.huang.githubsearchuserapi.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.huang.githubsearchuserapi.R

fun ImageView.setImageUrl(url: String) {
    Glide.with(context)
        .load(url)
        .dontAnimate()
        .placeholder(R.drawable.ic_baseline_person_24)
        .error(R.drawable.ic_baseline_person_24)
        .centerCrop()
        .into(this)
}