package me.syahdilla.putra.sholeh.storyappdicoding.utils

import android.view.View
import android.view.ViewGroup

fun View.getAllChildren(onChild: (View) -> Unit) {
    if (this !is ViewGroup) onChild(this)
    else for (index in 0 until this.childCount) {
        val child = this.getChildAt(index)
        child.getAllChildren(onChild)
    }
}