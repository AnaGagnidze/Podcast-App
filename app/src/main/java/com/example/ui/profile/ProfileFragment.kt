package com.example.ui.profile

import android.util.Log.i
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.BaseFragment
import com.example.extensions.setPhotoUrl
import com.example.podcasts.R
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


    private lateinit var adapter: ProfileAdapter

    private val viewModel: ProfileViewModel by viewModels()

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
        viewModel.load()
        bind()

    }

    private fun bind(){

        adapter = ProfileAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.allFavoritePodcast.observe(viewLifecycleOwner){
            adapter.data = it
        }
        adapter.podcastClick  = {
            goDetailFragment(it)
        }

    }

    private fun goDetailFragment(id: String){
        setFragmentResult("podcastKey", bundleOf("podcastId" to id))
        findNavController().navigate(R.id.action_profileFragment_to_episodeFragment)
    }


}