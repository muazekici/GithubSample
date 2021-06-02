package com.muazekici.n11sample.ui.binding_adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImageFromUrl(v: ImageView, url: String?) {
    url?.let {
        Glide.with(v).load(url).into(v)
    }
}