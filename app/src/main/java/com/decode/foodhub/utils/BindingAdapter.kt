package com.decode.foodhub.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("app:load")
fun load(imageView: ImageView, url: String?) {
    imageView.load(url) {
        crossfade(true)
        crossfade(500)
    }
}