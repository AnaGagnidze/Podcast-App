package com.example.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repo.AuthRepository
import com.example.user_data.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userInfo: UserInfo
) : ViewModel() {

    fun signOut() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                authRepository.signOut()
            }
        }
    }

    fun getUsername(email: String) = userInfo.getUsername(email)


}