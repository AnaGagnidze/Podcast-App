package com.example.user_data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInfo @Inject constructor(@ApplicationContext context: Context){

    private val sharedPreference: SharedPreferences by lazy {
        context.getSharedPreferences("user_details", Context.MODE_PRIVATE)
    }

    fun saveUsername(email: String, username: String){
        sharedPreference.edit().putString(email, username).apply()
    }

    fun getUsername(email: String) = sharedPreference.getString(email, "")
}