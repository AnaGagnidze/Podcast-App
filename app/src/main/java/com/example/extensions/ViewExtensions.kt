package com.example.extensions

import android.view.View

fun View.gone(){
    this.visibility = View.INVISIBLE
}

fun View.show(){
    this.visibility = View.VISIBLE
}