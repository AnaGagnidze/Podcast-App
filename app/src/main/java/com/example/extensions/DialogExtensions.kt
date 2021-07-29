package com.example.extensions

import android.app.Dialog
import android.view.Window
import android.view.WindowManager

fun Dialog.setUp(dialogView: Int){
    window!!.setBackgroundDrawableResource(android.R.color.transparent)
    window!!.requestFeature(Window.FEATURE_NO_TITLE)
    setContentView(dialogView)
    window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
    window!!.attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
}