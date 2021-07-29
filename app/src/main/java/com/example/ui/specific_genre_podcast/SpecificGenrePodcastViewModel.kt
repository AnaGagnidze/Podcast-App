package com.example.ui.specific_genre_podcast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.Podcasts
import com.example.repo.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SpecificPodcastViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository
) : ViewModel() {


    private var _podcasts = MutableLiveData<List<Podcasts>>()
    val podcasts: LiveData<List<Podcasts>> get() = _podcasts



    fun load(id: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            val data = podcastRepository.getSpecificGenrePodcasts(id)
            _podcasts.postValue(data.data?.podcasts!!)
        }
    }


}