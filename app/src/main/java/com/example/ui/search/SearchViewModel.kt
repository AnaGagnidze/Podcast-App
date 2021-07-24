package com.example.ui.search

import android.util.Log.i
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.search.Result
import com.example.repo.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository
): ViewModel() {

    private var _podcast = MutableLiveData<List<Result>>()
    val podcast: LiveData<List<Result>> get() = _podcast


    fun searchPodcast(podcastName: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = podcastRepository.getSearchPodcast(podcastName)
                i("shesvla", " viumodel scopshi data = ${data.data?.results}")
                _podcast.postValue(data.data?.results)
            }
        }
    }



}