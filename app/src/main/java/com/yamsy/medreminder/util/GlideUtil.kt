package com.yamsy.medreminder.util

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yamsy.medreminder.R

object GlideUtil {

    fun loadThumbNail(iv: ImageView, uri : Uri) {
        Glide.with(iv)
            .asBitmap()
            .load(uri)
            .centerCrop()
            .placeholder(R.drawable.no_image)
            .into(iv)
    }
}