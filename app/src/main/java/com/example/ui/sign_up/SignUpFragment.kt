package com.example.ui.sign_up

import android.app.Activity
import android.content.Intent
import android.util.Log.i
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.base.BaseFragment
import com.example.extensions.checkEmail
import com.example.extensions.gone
import com.example.extensions.setSpannedString
import com.example.extensions.show
import com.example.podcasts.R
import com.example.podcasts.databinding.FragmentSignUpBinding
import com.example.util.usecases.Status
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

typealias strings = R.string
typealias colors = R.color

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val signUpViewModel: SignUpViewModel by viewModels()

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun setUpFragment() {
        setTexts()
        setClicks()
        binding.progressBar.isVisible = false
    }


    private fun setTexts() {
        binding.logInHere.setSpannedString(
            arrayOf(
                getString(strings.has_account),
                getString(strings.log_in)
            ), arrayOf(colors.text_gray, colors.main_pink)
        )

        binding.termsOfServiceTxtV.setSpannedString(
            arrayOf(
                getString(strings.agree_to_tos),
                getString(strings.terms_of_service)
            ), arrayOf(colors.text_gray, colors.main_pink)
        )

        binding.signUpBtn.authButton.text = getString(strings.sign_up)
    }

    private fun setClicks(){
        val signInIntent = googleSignInClient.signInIntent
        binding.signUpBtn.authButton.setOnClickListener {
            binding.signUpBtn.authButton.isClickable = false
            signUp()
            binding.progressBar.show()
            i("userN", binding.userNameEmail.text.toString())
        }

        binding.googleAuthBtn.setOnClickListener {
            resultLauncher.launch(signInIntent)
        }

        binding.logInHere.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.termsOfServiceTxtV.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_termsAndCondFragment)
        }
    }
    private fun signUp(){
        val email = binding.edtTxtEmail.text.toString()
        val password = binding.edtTxtPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()){
            if (email.checkEmail()){
                signUpViewModel.signUp(email, password)
            }else{
                binding.edtTxtEmail.error = getString(strings.incorrect_email_format)
            }
        }else{
            showDialog(getString(strings.fill_all_fields))
        }

        observe()

    }

    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK){
            val data = it.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                signUpViewModel.logInWithGoogle(account).observe(viewLifecycleOwner, {result ->
                    when(result.status){
                        Status.SUCCESS -> {
                            binding.signUpBtn.authButton.isClickable = true
                            signUpViewModel.saveUsername(account.email!!, account.displayName?: "No Username")
                            findNavController().navigateUp()
                            binding.progressBar.gone()
                        }
                        Status.ERROR -> {
                            binding.signUpBtn.authButton.isClickable = true
                            binding.progressBar.gone()
                            result.errorMessage?.let { it1 -> showDialog(it1) }
                        }
                        Status.LOADING -> {
                            binding.progressBar.show()
                            binding.signUpBtn.authButton.isClickable = false
                        }
                    }

                })
            } catch (e: ApiException) {
                binding.progressBar.gone()
                showDialog(e.message.toString())
            }
        }
    }

    private fun observe(){
        signUpViewModel._signUpLiveData.observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    binding.progressBar.gone()
                    binding.signUpBtn.authButton.isClickable = true
                    signUpViewModel.saveUsername(firebaseAuth.currentUser?.email!!, binding.userNameEmail.text.toString())
                    if (firebaseAuth.currentUser!!.isEmailVerified){
                        findNavController().navigateUp()
                    }else{
                        showDialog(getString(strings.verify_email))
                    }
                }
                Status.ERROR -> {
                    binding.signUpBtn.authButton.isClickable = true
                    it.errorMessage?.let { it1 -> showDialog(it1) }
                    binding.progressBar.gone()
                }
                Status.LOADING -> {
                    binding.progressBar.show()
                    binding.signUpBtn.authButton.isClickable = false
                }
            }
        })
    }

}