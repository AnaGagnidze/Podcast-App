package com.example.ui.podcast_genre

import android.util.Log
import android.util.Log.d
import android.util.Log.i
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.genre.Genre
import com.example.model.genre.PodcastGenre
import com.example.repo.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GenrePodcastViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository
) : ViewModel() {


    private var _genres = MutableLiveData<List<Genre?>>()
    val genres: LiveData<List<Genre?>> get() = _genres




      fun load(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = podcastRepository.getGenre()
                _genres.postValue(data.data?.genres)

            }
        }
    }


}