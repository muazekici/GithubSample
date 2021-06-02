package com.muazekici.n11sample.ui.exts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T> inflateForListView(layoutId: Int, parent: ViewGroup): T {
    return DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
}
