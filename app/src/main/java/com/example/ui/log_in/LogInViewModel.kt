package com.example.ui.log_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repo.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {

    private var logInLiveData = MutableLiveData<Boolean>()
    val _logInLiveData: LiveData<Boolean> = logInLiveData


    fun logIn(email: String, password: String){
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO){
                loggingIn(email, password)
            }

            data.addOnCompleteListener {
                logInLiveData.postValue(it.isSuccessful)
            }

        }
    }

    private fun loggingIn(email: String, password: String) = authRepository.logIn(email, password)


    fun logInWithGoogle(account: GoogleSignInAccount): MutableLiveData<Boolean> {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                loggingInWithGoogle(account)
            }

            data.addOnCompleteListener {
                logInLiveData.postValue(it.isSuccessful)
            }
        }
        return logInLiveData
    }

    private fun loggingInWithGoogle(account: GoogleSignInAccount) = authRepository.signInWithGoogle(account)



}