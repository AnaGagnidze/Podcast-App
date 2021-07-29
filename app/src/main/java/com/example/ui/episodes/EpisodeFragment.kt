package com.example.ui.episodes

import android.os.Bundle
import android.util.Log.i
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.BaseFragment
import com.example.model.specificPodcast.Episode
import com.example.podcasts.R
import com.example.podcasts.databinding.EpisodeFragmentBinding
import com.example.util.usecases.Status
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EpisodeFragment : BaseFragment<EpisodeFragmentBinding>(EpisodeFragmentBinding::inflate) {

    private val viewModel: EpisodeViewModel by viewModels()

    private lateinit var adapter: EpisodesAdapter

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("podcastKey") { requestKey, bundle ->
            val genre = bundle.getString("podcastId")
            genre?.let {

                viewModel.load(it) }


        }
    }

    private fun openPodcastPlayFragment(episode: Episode?) {
        setFragmentResult("episodesKey", bundleOf("episode" to episode))
        findNavController().navigate(R.id.action_episodeFragment_to_podcastPlayFragment)
    }


    override fun setUpFragment() {

        setUpRecyclerview()
        chooseFavoriteIcon()
        clickOnHeartIcon()
        binding.refresh.setOnRefreshListener {

            setUpRecyclerview()
        }

        binding.backArrowImg.setOnClickListener {
            findNavController().navigateUp()
        }


    }

    private fun chooseFavoriteIcon() {
        viewModel.ifExist.observe(viewLifecycleOwner) {
            if (it) {
                binding.favoriteIconImg.setImageResource(R.drawable.ic_heart_pink)
            } else {
                binding.favoriteIconImg.setImageResource(R.drawable.ic_heart)
            }

        }
    }


    private fun checkForDatabase(id: String) {
        viewModel.allPodcasts.observe(viewLifecycleOwner) {
            viewModel.checker(it, id)
        }
    }


    private fun clickOnHeartIcon() {
        binding.favoriteIconImg.setOnClickListener {

            if (viewModel.ifExist.value!!) {
                viewModel.currentPodcast.value?.let { it1 ->
                    viewModel.delete(it1)

                }
            } else {
                viewModel.currentPodcast.value?.let { it1 ->
                    viewModel.save(it1)

                }
            }
            chooseFavoriteIcon()

        }
    }

    private fun setUpRecyclerview() {

        adapter = EpisodesAdapter()
        binding.currentPodcastRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.currentPodcastRecyclerview.adapter = adapter

        viewModel.specificPodcast.observe(viewLifecycleOwner) {
            binding.refresh.isRefreshing = it.loading
            when (it.status) {
                Status.SUCCESS -> {
                    adapter.data = it.data!!.episodes
                    it.data.id?.let { it1 -> checkForDatabase(it1) }
                }
                Status.ERROR -> {
                }
                Status.LOADING -> {
                }
            }


        }

        adapter.episodeClick = {
            openPodcastPlayFragment(it)
        }

    }


}

