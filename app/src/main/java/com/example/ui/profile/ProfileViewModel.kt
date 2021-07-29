package com.example.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.favorites.FavoritePodcast
import com.example.repo.FavPodcastRepository
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.viewModelScope
import com.example.repo.AuthRepository
import com.example.user_data.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private  var favPodcastRepository: FavPodcastRepository,
    private var firebaseAuth: FirebaseAuth
): ViewModel() {


    private var _allFavoritePodcast = MutableLiveData<List<FavoritePodcast>>()
    val  allFavoritePodcast: LiveData<List<FavoritePodcast>> get() = _allFavoritePodcast



    fun load(){

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = favPodcastRepository.getAllPodcasts(firebaseAuth.currentUser?.email!!)
                _allFavoritePodcast.postValue(data)

            }
        }


    }





class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userInfo: UserInfo
) : ViewModel() {

    fun signOut() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                authRepository.signOut()
            }
        }
    }

    fun getUsername(email: String) = userInfo.getUsername(email)


}