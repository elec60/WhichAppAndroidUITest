package com.hashem.mousavi.mywhichappandroidtest

import android.os.Build
import android.view.View

/**
 * Extension method to set an onclick listener
 */
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener { block(it as T) }

fun View?.show() {
    this?.visibility = View.VISIBLE
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.hide() {
    this?.visibility = View.INVISIBLE
}

object Singleton{
    var density = 1f
}

fun dp(value: Int): Int {
    return Math.ceil((Singleton.density * value).toDouble()).toInt()
}

inline fun supportsLollipop(code: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        code()
    }
}