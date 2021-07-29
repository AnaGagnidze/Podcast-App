package com.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.podcasts.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Podcasts)
        setContentView(R.layout.activity_main)
    }
}