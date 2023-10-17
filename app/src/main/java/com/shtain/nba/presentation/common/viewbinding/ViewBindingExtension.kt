package com.shtain.nba.presentation.common.viewbinding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding


inline val ViewBinding.context: Context
    get() {
        return root.context
    }

inline fun <T : ViewBinding> ViewGroup.inflateVB(
    crossinline vbFactory: (LayoutInflater, ViewGroup, Boolean) -> T
): T {
    return vbFactory.invoke(LayoutInflater.from(context), this, false)
}