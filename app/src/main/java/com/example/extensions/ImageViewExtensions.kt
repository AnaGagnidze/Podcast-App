package com.example.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.setPhotoUrl(url:String){
    Glide.with(context).load(url).apply(RequestOptions.circleCropTransform()).into(this)
}