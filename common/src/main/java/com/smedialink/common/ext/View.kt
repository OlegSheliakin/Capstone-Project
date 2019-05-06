package com.smedialink.common.ext

import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun View.gone(isGone: Boolean = true) {
    visibility = if (isGone) View.GONE else View.VISIBLE
}

fun View.visible(isVisible: Boolean = true) {
    visibility = if (isVisible) View.INVISIBLE else View.VISIBLE
}

fun FloatingActionButton.showExt(
        shouldShow: Boolean,
        visibilityChangedListener: FloatingActionButton.OnVisibilityChangedListener? = null) {
    if (shouldShow) {
        this.show(visibilityChangedListener)
    } else {
        this.hide(visibilityChangedListener)
    }
}
