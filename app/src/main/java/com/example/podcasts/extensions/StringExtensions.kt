package com.example.podcasts.extensions

fun String.checkEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}