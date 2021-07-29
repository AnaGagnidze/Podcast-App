package com.example.ui.profile

import android.util.Log.i
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.base.BaseFragment
import com.example.extensions.setPhotoUrl
import com.example.podcasts.R
import com.example.podcasts.databinding.ProfileFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate) {

    private val profileViewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun setUpFragment() {
        setClicks()
        setUserInfo()
    }

    private fun setClicks(){
        binding.btnSignOut.setOnClickListener {
            profileViewModel.signOut()
            val navController = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navController.navController.navigate(R.id.action_global_logInFragment)
        }
    }
    private fun setUserInfo(){
        i("userEm", "${firebaseAuth.currentUser?.email}")
        binding.userNameTxtV.text = profileViewModel.getUsername(firebaseAuth.currentUser?.email!!)
        binding.emailTxtV.text = firebaseAuth.currentUser?.email
        binding.profilePhotoImgV.setPhotoUrl(firebaseAuth.currentUser?.photoUrl)
    }


}