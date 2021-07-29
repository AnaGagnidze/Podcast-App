package com.example.ui.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repo.AuthRepository
import com.example.user_data.UserInfo
import com.example.util.usecases.Resource
import com.example.util.usecases.Status
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

    private var signUpLiveData = MutableLiveData<Resource<String>>()
    val _signUpLiveData: LiveData<Resource<String>> = signUpLiveData

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                authRepository.signUp(email, password)
            }

            data.addOnCompleteListener {
                if (it.isSuccessful){
                    val user = firebaseAuth.currentUser
                    user?.sendEmailVerification()?.addOnCompleteListener {task ->
                        signUpLiveData.postValue(Resource(Status.LOADING, null, null, true))
                        if (it.isSuccessful){
                            signUpLiveData.postValue(
                                Resource(
                                    Status.SUCCESS,
                                    null,
                                    "Verification email has been sent",
                                    false
                                )
                            )
                        }else{
                            signUpLiveData.postValue(Resource(Status.ERROR, null, task.exception?.message.toString(), false))
                        }
                    }
                }
            }
        }
    }

    fun logInWithGoogle(account: GoogleSignInAccount): MutableLiveData<Resource<String>> {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                loggingInWithGoogle(account)
            }

            data.addOnCompleteListener {
                signUpLiveData.postValue(Resource(Status.LOADING, null, null, true))
                if (it.isSuccessful){

                    signUpLiveData.postValue(Resource(Status.SUCCESS, null, "Success", false))
                }else{
                    signUpLiveData.postValue(Resource(Status.ERROR, null, it.exception?.message.toString(), false))
                }
            }
        }
        return signUpLiveData
    }

    private fun loggingInWithGoogle(account: GoogleSignInAccount) =
        authRepository.signInWithGoogle(account)

    fun saveUsername(email: String, username: String){
        userInfo.saveUsername(email, username)
    }

}