package com.example.podcasts.auth.log_in

import com.example.podcasts.BaseFragment
import com.example.podcasts.R
import com.example.podcasts.databinding.FragmentLogInBinding
import com.example.podcasts.extensions.setSpannedString
import dagger.hilt.android.AndroidEntryPoint


typealias strings = R.string
typealias colors = R.color

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    override fun setUpFragment() {
        setTexts()
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

}