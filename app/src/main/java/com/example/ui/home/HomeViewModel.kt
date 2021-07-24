package com.example.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.example.model.Podcasts
import com.example.model.random_podcast.RandomPod
import com.example.repo.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository
    ) : ViewModel() {

    private var _podcasts = MutableLiveData<List<Podcasts>>()
    val podcasts: LiveData<List<Podcasts>> get() = _podcasts

    private var _randomPodcasts = MutableLiveData<List<RandomPod?>>()

    val randomPodcasts: LiveData<List<RandomPod?>> get() = _randomPodcasts

    private var _similarPod = MutableLiveData<List<Podcasts>>()
    val similarPod: LiveData<List<Podcasts>> get() = _similarPod


    init {
        load()
        loadRandom()
        load(3)
    }

    private fun loadRandom(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val randomOne = podcastRepository.justListen()
                val randomTwo = podcastRepository.justListen()
                val randomThree = podcastRepository.justListen()
                val randomFour = podcastRepository.justListen()


                val data = listOf(
                    randomOne.data,
                    randomTwo.data,
                    randomThree.data,
                    randomFour.data)
                _randomPodcasts.postValue(data)
            }
        }
    }


    private fun load() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            val data = podcastRepository.getPopularPodcasts()
            _podcasts.postValue(data.data?.podcasts!!)
        }
    }


    private fun load(prm: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            val data = podcastRepository.getPopularPodcasts(prm)
            _similarPod.postValue(data.data?.podcasts!!)
        }
    }


}