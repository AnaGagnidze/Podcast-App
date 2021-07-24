package com.example.ui.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.specificPodcast.SpecificPodcast
import com.example.repo.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository
) : ViewModel() {


    private var _specificPodcast = MutableLiveData<SpecificPodcast>()

    val specificPodcast: LiveData<SpecificPodcast> get() = _specificPodcast


    fun load(id: String)= viewModelScope.launch {
        withContext(Dispatchers.IO){
            val data = podcastRepository.getSpecificPodcasts(id)
            _specificPodcast.postValue(data.data!!)

        }
    }



}