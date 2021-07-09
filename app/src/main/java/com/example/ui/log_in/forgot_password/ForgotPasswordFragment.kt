package com.example.podcasts.auth.log_in.forgot_password

import androidx.fragment.app.viewModels
import com.example.podcasts.BaseFragment
import com.example.podcasts.R
import com.example.podcasts.databinding.ForgotPasswordFragmentBinding
import com.example.podcasts.extensions.setSpannedString
import dagger.hilt.android.AndroidEntryPoint

typealias strings = R.string
typealias colors = R.color

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<ForgotPasswordFragmentBinding>(ForgotPasswordFragmentBinding::inflate) {

    private val forgotPasswordViewModel: ForgotPasswordViewModel by viewModels()

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

        binding.sendResetEmailBtn.authButton.text = getString(strings.reset_request)

    }


}