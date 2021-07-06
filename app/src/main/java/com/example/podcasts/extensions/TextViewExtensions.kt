package com.example.podcasts.extensions

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.content.ContextCompat

fun TextView.setSpannedString(strings: Array<String>, colors: Array<Int>) {
    val spannableString = SpannableString(strings.joinToString(""))
    var startIndex = 0
    for (i in strings.indices) {
        spannableString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(context, colors[i])
            ),
            startIndex, startIndex + strings[i].length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        startIndex += strings[i].length
    }
    text = spannableString
}