package com.example.ui.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repo.AuthRepository
import com.example.user_data.UserInfo
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firebaseAuth: FirebaseAuth,
    private val userInfo: UserInfo
) : ViewModel() {

    private var signUpLiveData = MutableLiveData<String>()
    val _signUpLiveData: LiveData<String> = signUpLiveData

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                authRepository.signUp(email, password)
            }

            data.addOnCompleteListener {
                if (it.isSuccessful){
                    val user = firebaseAuth.currentUser
                    user?.sendEmailVerification()?.addOnCompleteListener {task ->
                        if (it.isSuccessful){
                            signUpLiveData.postValue("Verification email had been sent")
                        }else{
                            signUpLiveData.postValue(task.exception?.message.toString())
                        }
                    }
                }
            }
        }
    }

    fun logInWithGoogle(account: GoogleSignInAccount): MutableLiveData<String> {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                loggingInWithGoogle(account)
            }

            data.addOnCompleteListener {
                if (it.isSuccessful){
                    signUpLiveData.postValue("Success")
                }else{
                    signUpLiveData.postValue(it.exception?.message.toString())
                }
            }
        }
        return signUpLiveData
    }

    private fun loggingInWithGoogle(account: GoogleSignInAccount) =
        authRepository.signInWithGoogle(account)

    fun saveUsername(username: String){
        userInfo.saveUsername(username)
    }

}