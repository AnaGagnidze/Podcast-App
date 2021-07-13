package com.example.ui.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repo.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {

    private var signUpLiveData = MutableLiveData<Boolean>()
    val _signUpLiveData: LiveData<Boolean> = signUpLiveData

    fun signUp(email: String, password: String){
        viewModelScope.launch {
            val data= withContext(Dispatchers.IO){
                signingUp(email, password)
            }

            data.addOnCompleteListener {
                signUpLiveData.postValue(it.isSuccessful)
            }
        }
    }

    private fun signingUp(email: String, password: String) = authRepository.signUp(email, password)

    fun logInWithGoogle(account: GoogleSignInAccount): MutableLiveData<Boolean> {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                loggingInWithGoogle(account)
            }

            data.addOnCompleteListener {
                signUpLiveData.postValue(it.isSuccessful)
            }
        }
        return signUpLiveData
    }

    private fun loggingInWithGoogle(account: GoogleSignInAccount) = authRepository.signInWithGoogle(account)

}