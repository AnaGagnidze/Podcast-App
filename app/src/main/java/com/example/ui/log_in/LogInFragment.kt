package com.example.ui.log_in

import android.app.Activity
import android.content.Intent
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
import com.example.podcasts.databinding.FragmentLogInBinding
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
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {


    private val logInViewModel: LogInViewModel by viewModels()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun setUpFragment() {
        setTexts()
        setClicks()
        binding.progressBar.isVisible =false
    }

    override fun onStart() {
        super.onStart()
        checkLoggedInUser()
    }

    private fun checkLoggedInUser(){
        if (firebaseAuth.currentUser != null){
            findNavController().navigate(R.id.action_logInFragment_to_bottomFragment)
        }
    }

    private fun setTexts() {
        binding.signUpHere.setSpannedString(
            arrayOf(
                getString(strings.no_account),
                getString(strings.sign_up)
            ), arrayOf(colors.text_gray, colors.main_pink)
        )

        binding.logInBtn.authButton.text = getString(strings.log_in)
    }

    private fun setClicks(){
        val signInIntent = googleSignInClient.signInIntent
        binding.logInBtn.authButton.setOnClickListener {
            binding.logInBtn.authButton.isClickable = false
            logIn()
            binding.progressBar.show()

        }
        binding.googleAuthBtn.setOnClickListener {
            resultLauncher.launch(signInIntent)
        }

        binding.signUpHere.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
        }

        binding.forgotPassTxtV.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_forgotPasswordFragment)
        }
    }

    private fun logIn(){
        val email = binding.edtTxtEmail.text.toString()
        val password = binding.edtTxtPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()){
            if (email.checkEmail()){
                logInViewModel.logIn(email, password)
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
                logInViewModel.logInWithGoogle(account).observe(viewLifecycleOwner, {result ->
                    when(result.status){
                        Status.SUCCESS -> {
                            binding.logInBtn.authButton.isClickable = true
                            binding.progressBar.gone()
                            findNavController().navigate(R.id.action_logInFragment_to_bottomFragment)
                        }
                        Status.ERROR -> {
                            binding.logInBtn.authButton.isClickable = true
                            binding.progressBar.gone()
                            result.errorMessage?.let { error -> showDialog(error) }
                        }
                        Status.LOADING -> {
                            binding.progressBar.show()
                            binding.logInBtn.authButton.isClickable = false
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
        logInViewModel._logInLiveData.observe(viewLifecycleOwner, {
            binding.progressBar.isVisible = it.loading
            when(it.status){
                Status.SUCCESS -> {
                    binding.progressBar.gone()
                    binding.logInBtn.authButton.isClickable = true
                    if(findNavController().currentDestination?.id == R.id.logInFragment){
                        findNavController().navigate(R.id.action_logInFragment_to_bottomFragment)
                    }

                }
                Status.ERROR -> {
                    binding.progressBar.gone()
                    binding.logInBtn.authButton.isClickable = true
                    it.errorMessage?.let { error -> showDialog(error) }
                }
                Status.LOADING -> {
                    binding.progressBar.show()
                    binding.logInBtn.authButton.isClickable = false
                }
            }
        })
    }

}