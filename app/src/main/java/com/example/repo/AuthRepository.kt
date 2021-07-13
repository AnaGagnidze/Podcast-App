package com.example.repo

import com.example.network.GoogleAuth.FirebaseSource
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseSrc: FirebaseSource
) {

    fun signUp(email: String, password: String): Task<AuthResult>{
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun logIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }


    fun signOut(){
        Firebase.auth.signOut()
    }

    fun signInWithGoogle(acct: GoogleSignInAccount) =
        firebaseSrc.signInWithGoogle(acct)

}