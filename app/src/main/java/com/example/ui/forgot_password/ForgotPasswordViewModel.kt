package com.example.ui.forgot_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {

    private var forgotPasswordLiveData = MutableLiveData<String>()
    val _logInLiveData: LiveData<String> = forgotPasswordLiveData

    fun forgotPass(email: String){
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO){
                authRepository.forgotPassword(email)
            }

            data.addOnCompleteListener {
                if (it.isSuccessful){
                    forgotPasswordLiveData.postValue("Success")
                }else{
                    forgotPasswordLiveData.postValue(it.exception?.message.toString())
                }
            }
        }
    }
}