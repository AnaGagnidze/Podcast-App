package com.example.extensions

import androidx.core.text.HtmlCompat
import com.google.common.html.HtmlEscapers

fun String.checkEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}



fun String.cleanText():String{
   return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}
