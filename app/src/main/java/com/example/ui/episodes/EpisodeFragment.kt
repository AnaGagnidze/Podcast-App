package com.example.ui.episodes

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.base.BaseFragment
import com.example.model.specificPodcast.Episode
import com.example.podcasts.R
import com.example.podcasts.databinding.EpisodeFragmentBinding
import com.example.util.usecases.Status
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

        binding.refresh.setOnRefreshListener {
            setUpScreen()
            setUpRecyclerview()
        }

        binding.backArrowImg.setOnClickListener{
            findNavController().navigateUp()
        }


    }

    private fun setUpRecyclerview(){

        adapter = EpisodesAdapter()
        binding.currentPodcastRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.currentPodcastRecyclerview.adapter = adapter
        viewModel.specificPodcast.observe(viewLifecycleOwner){
            adapter.data = it.data!!.episodes

        }

        adapter.episodeClick = {
            openPodcastPlayFragment(it)
        }

    }

    private fun setUpScreen(){
        viewModel.specificPodcast.observe(viewLifecycleOwner){
            binding.refresh.isRefreshing = it.loading
            when (it.status){
                Status.SUCCESS -> {
                    binding.podcastNameTxt.text = it.data!!.title
                    binding.desciptionTxt.text = it.data.description
                    Glide.with(requireContext()).load(it.data.image)
                        .into(binding.imgView)
                }
                Status.ERROR -> {}
                Status.LOADING -> {}
            }

        }
    }


}