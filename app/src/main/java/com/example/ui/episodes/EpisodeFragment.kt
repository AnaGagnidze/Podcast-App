package com.example.ui.episodes

import android.os.Bundle
import android.util.Log.i
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.GeneratedAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.base.BaseFragment
import com.example.model.genre.Genre
import com.example.model.specificPodcast.Episode
import com.example.podcasts.R
import com.example.podcasts.databinding.EpisodeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeFragment : BaseFragment<EpisodeFragmentBinding>(EpisodeFragmentBinding::inflate) {

    private val viewModel: EpisodeViewModel by viewModels()

    private lateinit var adapter: EpisodesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("podcastKey"){requestKey, bundle ->
            val genre = bundle.getString("podcastId")
            genre?.let { viewModel.load(it) }

        }
    }

    private fun openPodcastPlayFragment(episode: Episode?){

        setFragmentResult("episodesKey", bundleOf("episode" to episode))
        findNavController().navigate(R.id.action_episodeFragment_to_podcastPlayFragment)


    }


    override fun setUpFragment() {

        setUpScreen()
        setUpRecyclerview()


    }

    private fun setUpRecyclerview(){

        adapter = EpisodesAdapter()
        binding.currentPodcastRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.currentPodcastRecyclerview.adapter = adapter
        viewModel.specificPodcast.observe(viewLifecycleOwner){

            adapter.data = it.episodes

        }

        adapter.episodeClick = {
            openPodcastPlayFragment(it)
        }

    }

    private fun setUpScreen(){
        viewModel.specificPodcast.observe(viewLifecycleOwner){
            binding.podcastNameTxt.text = it.title
            binding.desciptionTxt.text = it.description
            Glide.with(requireContext()).load(it.image)
                .into(binding.imgView)
        }
    }


}