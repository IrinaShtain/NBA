package com.shtain.nba.presentation.common.extensions

import android.view.View


fun View.toGone() {
    visibility = View.GONE
}

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toInvisible() {
    visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.isGone(): Boolean {
    return visibility == View.GONE
}

fun View.visibleOrGoneIf(condition: Boolean) {
    if (condition) {
        toVisible()
    } else {
        toGone()
    }
}