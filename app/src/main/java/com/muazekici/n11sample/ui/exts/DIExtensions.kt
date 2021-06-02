package com.muazekici.n11sample.ui.exts

import android.content.Context
import com.muazekici.n11sample.ui.utils.DaggerComponentOwner


fun <T> Context.daggerComponent():T {
    return (this as DaggerComponentOwner<*>).component as T
}