package com.atg.demorch.ext

import android.widget.ImageView
import com.atg.demorch.R
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context).load(url).placeholder(R.drawable.craft_beer_clip).into(this)
}
