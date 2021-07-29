package com.example.ui.profile

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.BaseFragment
import com.example.podcasts.R
import com.example.podcasts.databinding.ProfileFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate) {

    private lateinit var adapter: ProfileAdapter

    private val viewModel: ProfileViewModel by viewModels()

    override fun setUpFragment() {

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