package com.example.ui.podcast_play

import android.animation.ValueAnimator
import android.content.ComponentName
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.format.DateUtils
import android.text.method.ScrollingMovementMethod
import android.view.animation.LinearInterpolator
import android.widget.SeekBar
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.base.BaseFragment
import com.example.model.specificPodcast.Episode
import com.example.podcasts.R
import com.example.podcasts.databinding.PodcastPlayFragmentBinding
import com.example.service.PodplayMediaService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PodcastPlayFragment : BaseFragment<PodcastPlayFragmentBinding>(PodcastPlayFragmentBinding::inflate) {



    private lateinit var mediaBrowser: MediaBrowserCompat
    private var mediaControllerCallback: MediaControllerCallback? = null
    private var progressAnimator: ValueAnimator? = null
    private var episodeDuration: Long = 0L
    private var playerSpeed: Float = 1.0f
    private var draggingScrubber: Boolean = false
    private var mediaPlayer: MediaPlayer? = null
    private var playOnPrepare: Boolean = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("episodesKey"){ requestKey, bundle ->
            val episode = bundle.getParcelable<Episode>("episode")
            episode?.let {

                initMediaBrowser()
                setupControls(it)
                updateControls(it)
                if (mediaBrowser.isConnected) {

                    if (MediaControllerCompat.getMediaController(activity as FragmentActivity) == null) {
                        registerMediaController(mediaBrowser.sessionToken)
                    }
                    updateControlsFromController()
                } else {
                    mediaBrowser.connect()
                }


            }


        }
    }

    override fun setUpFragment() {
        binding.playToggleButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        binding.backArrowImg.setOnClickListener{
            findNavController().navigateUp()
        }


    }

    override fun onStop() {
        super.onStop()
        val fragmentActivity = activity as FragmentActivity
        progressAnimator?.cancel()
        if (MediaControllerCompat.getMediaController(fragmentActivity) != null){
            mediaControllerCallback?.let {
                MediaControllerCompat.getMediaController(activity as FragmentActivity)
                    .unregisterCallback(it)
            }
        }
        if (!fragmentActivity.isChangingConfigurations){
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }






    private fun updateControls(randomPod: Episode) {
        // 1
        binding.episodeTitleTextView.text = randomPod.title
        // 2


        binding.episodeDescTextView.text = randomPod.description
        binding.episodeDescTextView.movementMethod = ScrollingMovementMethod()
        // 3

        Glide.with(this)
            .load(randomPod.image)
            .into(binding.podcastBGimg)
        Glide.with(this)
            .load(randomPod.image)
            .into(binding.podcastImg)


        mediaPlayer?.let {
            updateControlsFromController()
        }
    }


    private fun seekBy(seconds: Int) {

        val controller = MediaControllerCompat.getMediaController(activity as FragmentActivity)
        val newPosition = controller.playbackState.position + seconds * 1000
        controller.transportControls.seekTo(newPosition)
        /*handleStateChange(controller.playbackState.state,newPosition,playerSpeed)*/
    }




    private fun togglePlayPause(randomPod: Episode) {

        playOnPrepare = true

        val controller = MediaControllerCompat.getMediaController(activity as FragmentActivity)
        if (controller.playbackState != null) {
            if (controller.playbackState.state == PlaybackStateCompat.STATE_PLAYING) {
                controller.transportControls.pause()
                binding.playToggleButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            } else {
                startPlaying(randomPod)
                binding.playToggleButton.setImageResource(R.drawable.ic_baseline_pause_24)
            }
        } else {
            startPlaying(randomPod)
            binding.playToggleButton.setImageResource(R.drawable.ic_baseline_pause_24)
        }
    }




    private fun initMediaBrowser() {

        mediaBrowser = MediaBrowserCompat(activity as FragmentActivity,
            ComponentName(activity as FragmentActivity, PodplayMediaService::class.java),
            MediaBrowserCallBacks(),
            null)
    }


    private fun setupControls(randomPod: Episode) {
        binding.playToggleButton.setOnClickListener {
            togglePlayPause(randomPod)
        }

        binding.forwardButton.setOnClickListener {
            seekBy(30)
        }
        binding.replayButton.setOnClickListener {
            seekBy(-10)
        }
        // 1
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // 2
                binding.currentTimeTextView.text = DateUtils.formatElapsedTime((progress / 1000).toLong())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // 3
                draggingScrubber = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // 4
                draggingScrubber = false
                // 5

                val controller = MediaControllerCompat.getMediaController(activity as FragmentActivity)
                if (controller.playbackState != null) {
                    // 6
                    controller.transportControls.seekTo(seekBar.progress.toLong())
                } else {
                    // 7
                    seekBar.progress = 0
                }
            }
        })
    }


    inner class MediaBrowserCallBacks : MediaBrowserCompat.ConnectionCallback() {

        override fun onConnected() {
            super.onConnected()
            registerMediaController(mediaBrowser.sessionToken)
            updateControlsFromController()
        }

        override fun onConnectionSuspended() {
            super.onConnectionSuspended()
            println("onConnectionSuspended")
        }

        override fun onConnectionFailed() {
            super.onConnectionFailed()
            println("onConnectionFailed")
        }
    }




    private fun registerMediaController(token: MediaSessionCompat.Token) {
        val mediaController = MediaControllerCompat(activity as FragmentActivity, token)
        MediaControllerCompat.setMediaController(activity as FragmentActivity, mediaController)
        mediaControllerCallback = MediaControllerCallback()
        mediaController.registerCallback(mediaControllerCallback!!)
    }


    inner class MediaControllerCallback : MediaControllerCompat.Callback() {



        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
            metadata?.let { updateControlsFromMetadata(it) }
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            val currentState = state ?: return
            handleStateChange(currentState.state, currentState.position, 1.0f)
        }
    }





    private fun updateControlsFromController() {

        val controller = MediaControllerCompat.getMediaController(activity as FragmentActivity)
        if (controller != null) {
            val metadata = controller.metadata
            if (metadata != null) {
                handleStateChange(controller.playbackState.state,
                    controller.playbackState.position, playerSpeed)
                updateControlsFromMetadata(controller.metadata)
            }
        }
    }

    private fun updateControlsFromMetadata(metadata: MediaMetadataCompat) {
        episodeDuration = metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION)
        binding.endTimeTextView.text = DateUtils.formatElapsedTime((episodeDuration / 1000))
        binding.seekBar.max = episodeDuration.toInt()
    }

    private fun handleStateChange(state: Int, position: Long, speed: Float) {
        progressAnimator?.let {
            it.cancel()
            progressAnimator = null
        }
        val isPlaying = state == PlaybackStateCompat.STATE_PLAYING
        binding.playToggleButton.isActivated = isPlaying

        val progress = position.toInt()
        binding.seekBar.progress = progress

        if (isPlaying){
            animateScrubber(progress, speed)
        }


    }

    private fun animateScrubber(progress: Int, speed: Float) {

        val timeRemaining = ((episodeDuration - progress) / speed).toInt()
        if (timeRemaining < 0) {
            return
        }

        progressAnimator = ValueAnimator.ofInt(progress, episodeDuration.toInt())
        progressAnimator?.let { animator ->
            animator.duration = timeRemaining.toLong()
            animator.interpolator = LinearInterpolator()
            animator.addUpdateListener {
                if (draggingScrubber) {
                    animator.cancel()
                } else {
                    binding.seekBar.progress = animator.animatedValue as Int
                }
            }
            animator.start()
        }
    }

    private fun startPlaying(episodeViewData: Episode) {

        val controller = MediaControllerCompat.getMediaController(activity as FragmentActivity)
        val bundle = Bundle()
        bundle.putString(MediaMetadataCompat.METADATA_KEY_TITLE, episodeViewData.title)
        bundle.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, episodeViewData.link)
        bundle.putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, episodeViewData.image)


        controller.transportControls.playFromUri(Uri.parse(episodeViewData.audio), bundle)
    }












}