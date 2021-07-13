package com.example.ui.sign_up

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.podcasts.R
import com.example.base.BaseFragment
import com.example.extensions.checkEmail
import com.example.extensions.setSpannedString
import com.example.podcasts.databinding.FragmentSignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

typealias strings = R.string
typealias colors = R.color

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val signUpViewModel: SignUpViewModel by viewModels()

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun setUpFragment() {
        setTexts()
        setClicks()
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
            signUp()
            observe()
        }

        binding.googleAuthBtn.setOnClickListener {
            resultLauncher.launch(signInIntent)
        }

        binding.logInHere.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    private fun signUp(){
        val email = binding.edtTxtEmail.text.toString()
        val password = binding.edtTxtPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()){
            if (email.checkEmail()){
                signUpViewModel.signUp(email, password)
            }else{
                Toast.makeText(requireContext(), "Email Format is incorrect", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
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
                    if (result == true){
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_signUpFragment_to_bottomFragment)
                    }else{
                        Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show()
                    }

                })
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observe(){
        signUpViewModel._signUpLiveData.observe(viewLifecycleOwner, {
            if (it == true){
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signUpFragment_to_bottomFragment)
            }else{
                Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show()
            }
        })
    }
}