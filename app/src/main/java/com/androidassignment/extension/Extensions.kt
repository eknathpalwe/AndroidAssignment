package com.androidassignment.extension

import android.widget.ImageView
import com.androidassignment.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * @param url - image url path
 * Load image into image view with placeholder and error if image is not loaded
 */
fun ImageView.loadImage(url: String) {
    Glide.with(context).load(url)
        .thumbnail(1.0f)
        .placeholder(R.mipmap.ic_launcher)
        .error(R.drawable.ic_error_img)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}