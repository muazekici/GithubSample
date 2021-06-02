package com.muazekici.n11sample.ui.exts

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.muazekici.n11sample.ui.utils.DaggerComponentOwner

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T =
    this.apply {
        arguments = Bundle().apply(argsBuilder)
    }
