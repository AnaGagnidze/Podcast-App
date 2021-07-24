package com.example.ui.podcast_play

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.example.base.BaseFragment
import com.example.model.specificPodcast.Episode
import com.example.podcasts.R
import com.example.podcasts.databinding.PodcastPlayFragmentBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PodcastPlayFragment : BaseFragment<PodcastPlayFragmentBinding>(PodcastPlayFragmentBinding::inflate) {

    private var player: SimpleExoPlayer? = null

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("episodesKey"){ requestKey, bundle ->
                val episode = bundle.getParcelable<Episode>("episode")
                episode?.let {
                    setUp(it)
                }


        }
    }

    override fun setUpFragment() {

    }

    private fun setUp(episode: Episode){
        Glide.with(requireContext()).load(episode.image).into(binding.podcastBGimg)
        Glide.with(requireContext()).load(episode.image).into(binding.podcastImg)
        binding.authorName.text = episode.title
        episode.audio?.let { onPlay(it) }
    }


    private fun onPlay(audio: String){
        player = SimpleExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.audioView.player = exoPlayer

                val mediaItem = MediaItem.fromUri(audio)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentWindow, playbackPosition)
                exoPlayer.prepare()
            }

    }


}