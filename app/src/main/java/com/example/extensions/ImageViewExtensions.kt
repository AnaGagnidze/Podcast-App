package com.example.extensions

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.podcasts.R

fun ImageView.setPhotoUrl(uri:Uri?){
    if (uri != null){
        Glide.with(context).load(uri).apply(RequestOptions.circleCropTransform()).placeholder(R.drawable.ic_outline_person).into(this)
    }else{
        setImageResource(R.drawable.ic_outline_person)
    }

}