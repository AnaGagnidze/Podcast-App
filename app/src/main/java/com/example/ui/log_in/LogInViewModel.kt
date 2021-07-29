package com.example.ui.log_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repo.AuthRepository
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
class LogInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var logInLiveData = MutableLiveData<Resource<String>>()
    val _logInLiveData: LiveData<Resource<String>> = logInLiveData


    fun logIn(email: String, password: String) {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                authRepository.logIn(email, password)
            }

            data.addOnCompleteListener {
                logInLiveData.postValue(Resource(Status.LOADING, null, null, true))
                if (it.isSuccessful) {
                    logInLiveData.postValue(Resource(Status.SUCCESS, null, "Success", false))
                } else {
                    logInLiveData.postValue(
                        Resource(
                            Status.ERROR,
                            null,
                            it.exception?.message.toString(),
                            false
                        )
                    )
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
                logInLiveData.postValue(Resource(Status.LOADING, null, null, true))
                if (it.isSuccessful) {
                    logInLiveData.postValue(Resource(Status.SUCCESS, null, "Success", false))
                } else {
                    logInLiveData.postValue(
                        Resource(
                            Status.ERROR,
                            null,
                            it.exception?.message.toString(),
                            false
                        )
                    )
                }
            }
        }
        return logInLiveData
    }

    private fun loggingInWithGoogle(account: GoogleSignInAccount) =
        authRepository.signInWithGoogle(account)


}