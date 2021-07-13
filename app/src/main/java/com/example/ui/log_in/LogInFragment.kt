package com.example.ui.log_in

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.base.BaseFragment
import com.example.extensions.checkEmail
import com.example.extensions.setSpannedString
import com.example.podcasts.R
import com.example.podcasts.databinding.FragmentLogInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


typealias strings = R.string
typealias colors = R.color

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {


    private val logInViewModel: LogInViewModel by viewModels()

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun setUpFragment() {
        setTexts()
        setClicks()
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
            logIn()
            observe()
        }
        binding.googleAuthBtn.setOnClickListener {
            resultLauncher.launch(signInIntent)
        }

        binding.signUpHere.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
        }
    }

    private fun logIn(){
        val email = binding.edtTxtEmail.text.toString()
        val password = binding.edtTxtPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()){
            if (email.checkEmail()){
                logInViewModel.logIn(email, password)
            }else{
                Toast.makeText(requireContext(), "Email format is incorrect", Toast.LENGTH_SHORT).show()
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
                logInViewModel.logInWithGoogle(account).observe(viewLifecycleOwner, {result ->
                    if (result == true){
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_logInFragment_to_bottomFragment)
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
        logInViewModel._logInLiveData.observe(viewLifecycleOwner, {
            if (it == true){
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_logInFragment_to_bottomFragment)
            }else{
                Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show()
            }
        })
    }

}