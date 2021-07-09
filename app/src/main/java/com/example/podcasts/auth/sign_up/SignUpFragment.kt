package com.example.podcasts.auth.sign_up

import com.example.podcasts.BaseFragment
import com.example.podcasts.R
import com.example.podcasts.databinding.FragmentSignUpBinding
import com.example.podcasts.extensions.setSpannedString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

typealias strings = R.string
typealias colors = R.color

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    override fun setUpFragment() {
        setTexts()
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
}