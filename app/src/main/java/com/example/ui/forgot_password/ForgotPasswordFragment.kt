package com.example.ui.forgot_password

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.base.BaseFragment
import com.example.extensions.checkEmail
import com.example.extensions.setSpannedString
import com.example.podcasts.R
import com.example.podcasts.databinding.ForgotPasswordFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

typealias strings = R.string
typealias colors = R.color

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<ForgotPasswordFragmentBinding>(ForgotPasswordFragmentBinding::inflate) {

    private val forgotPasswordViewModel: ForgotPasswordViewModel by viewModels()

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

        binding.sendResetEmailBtn.authButton.text = getString(strings.reset_request)

    }

    private fun setClicks(){
        binding.sendResetEmailBtn.authButton.setOnClickListener {
            sendResetEmail()
            observe()
        }

        binding.logInHere.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun sendResetEmail(){
        val email = binding.edtTxtEmail.text.toString()
        if (email.isNotEmpty()){
            if (email.checkEmail()){
                forgotPasswordViewModel.forgotPass(email)
            }
        }else{
            showDialog(getString(strings.fill_all_fields))
        }
    }

    private fun observe(){
        forgotPasswordViewModel._logInLiveData.observe(viewLifecycleOwner, {
            if (it == "Success"){
                findNavController().navigateUp()
            }else{
                showDialog(it)
            }
        })
    }


}