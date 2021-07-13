package com.example.ui.bottom_fragment

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.base.BaseFragment
import com.example.podcasts.R
import com.example.podcasts.databinding.BottomFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomFragment : BaseFragment<BottomFragmentBinding>(BottomFragmentBinding::inflate) {

    override fun setUpFragment() {
        setUpBottomNav()
    }

    private fun setUpBottomNav() {
        val navController = childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.navView.setupWithNavController(navController.navController)
    }


}