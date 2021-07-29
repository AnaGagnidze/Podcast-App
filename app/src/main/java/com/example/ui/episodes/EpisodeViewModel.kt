package com.example.ui.episodes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.favorites.FavoritePodcast
import com.example.model.specificPodcast.SpecificPodcast
import com.example.repo.PodcastRepository
import com.example.repo.FavPodcastRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository,
    private val roomRepository: FavPodcastRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {


    private var _specificPodcast = MutableLiveData<SpecificPodcast>()

    val specificPodcast: LiveData<SpecificPodcast> get() = _specificPodcast

    private var _allPodcasts = MutableLiveData<List<FavoritePodcast>>()

    val allPodcasts: LiveData<List<FavoritePodcast>> get() = _allPodcasts


    private var _currentPodcast = MutableLiveData<FavoritePodcast>()
     val currentPodcast: LiveData<FavoritePodcast> get() = _currentPodcast



    private var _ifExist = MutableLiveData<Boolean>()

     val ifExist: LiveData<Boolean> get() = _ifExist



    fun delete(favoritePodcast: FavoritePodcast){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Log.i("shesvla", "shevediw washlashi")
                roomRepository.deletePodcast(favoritePodcast)
            }
            getAllPodcasts(firebaseAuth.currentUser?.email!!)
            checker(allPodcasts.value!!, firebaseAuth.currentUser?.email!!)

        }
    }


    fun checker(list: List<FavoritePodcast>, id: String){

        for (i in list){
            if (i.podcastId == id){
                _ifExist.postValue(true)
            }else{
                _ifExist.postValue(false)
            }
        }

    }



    fun load(id: String)= viewModelScope.launch {
        withContext(Dispatchers.IO){
            val data = podcastRepository.getSpecificPodcasts(id)
            _specificPodcast.postValue(data.data!!)
            _currentPodcast.postValue(FavoritePodcast(
                data.data.id!!,
                firebaseAuth.currentUser?.email,
                data.data.image,
                data.data.title,
                data.data.description))

            getAllPodcasts(firebaseAuth.currentUser?.email!!)

        }
    }


    fun save(obj: FavoritePodcast)= viewModelScope.launch {
        withContext(Dispatchers.IO){
            Log.i("shesvla", "shevedi daseivebashi")
            roomRepository.addPodcast(obj)
        }
        getAllPodcasts(firebaseAuth.currentUser?.email!!)
        checker(allPodcasts.value!!, firebaseAuth.currentUser?.email!!)
    }


    fun getAllPodcasts(email: String)=viewModelScope.launch {

        val data = withContext(Dispatchers.IO){
            roomRepository.getAllPodcasts(email)
        }
        _allPodcasts.postValue(data)



    }












}