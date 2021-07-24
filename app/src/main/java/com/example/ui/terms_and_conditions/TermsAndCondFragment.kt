package com.example.ui.terms_and_conditions

import androidx.navigation.fragment.findNavController
import com.example.base.BaseFragment
import com.example.podcasts.databinding.FragmentTermsAndCondBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsAndCondFragment :
    BaseFragment<FragmentTermsAndCondBinding>(FragmentTermsAndCondBinding::inflate) {
    override fun setUpFragment() {
        binding.backArrowImg.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}