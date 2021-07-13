package com.example.repo

import android.util.Log.d
import android.util.Log.i
import com.example.model.PagingData
import com.example.model.genre.PodcastGenre
import com.example.model.random_podcast.RandomPod
import com.example.model.specificPodcast.SpecificPodcast
import com.example.network.PodcastNetwork
import com.example.util.usecases.Resource
import com.example.util.usecases.ResponseHandler
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class PodcastRepository @Inject constructor(
    private val podcastNetwork: PodcastNetwork,
    private val responseHandler: ResponseHandler) {

    suspend fun getPopularPodcasts(): Resource<PagingData> {
        val podcastResponse: Response<PagingData>
        try {
              podcastResponse = podcastNetwork.getPopularPodcasts(
                144,
                2,
                "us",
                0)
            if (podcastResponse.isSuccessful){
                podcastResponse.body()?.let {

                    return responseHandler.handleSuccess(it)
                }
                return responseHandler.handleDefaultException()
            }else{
                return responseHandler.handleDefaultException()
            }
        }catch (e : Exception){
            return responseHandler.handleException(e)
        }
    }

    suspend fun getSpecificGenrePodcasts(genre: Int):Resource<PagingData>{
            val specificPodcast: Response<PagingData>
        try {
            specificPodcast = podcastNetwork.getPopularPodcasts(
                genre,
                2,
                "us",
                0)
            if (specificPodcast.isSuccessful){
                specificPodcast.body()?.let {
                    return responseHandler.handleSuccess(it)
                }
                return responseHandler.handleDefaultException()
            }else{
                return responseHandler.handleDefaultException()
            }
        }catch (e : Exception){
            return responseHandler.handleException(e)
        }

    }

    suspend fun getSpecificPodcasts(id: String):Resource<SpecificPodcast>{
        val specificPodcast: Response<SpecificPodcast>
        try {
            specificPodcast = podcastNetwork.specificPodcast(id)
            if (specificPodcast.isSuccessful){
                specificPodcast.body()?.let {
                    return responseHandler.handleSuccess(it)
                }
                return responseHandler.handleDefaultException()
            }else{
                return responseHandler.handleDefaultException()
            }
        }catch (e : Exception){
            return responseHandler.handleException(e)
        }

    }




    suspend fun getPopularPodcasts(page: Int): Resource<PagingData> {
        val podcastResponse: Response<PagingData>
        try {
            podcastResponse = podcastNetwork.getPopularPodcasts(
                144,
                page,
                "us",
                0)
            if (podcastResponse.isSuccessful){
                podcastResponse.body()?.let {
                    return responseHandler.handleSuccess(it)
                }
                return responseHandler.handleDefaultException()
            }else{
                return responseHandler.handleDefaultException()
            }
        }catch (e : Exception){
            return responseHandler.handleException(e)
        }
    }



    suspend fun justListen():Resource<RandomPod>{
        val randomPodcast: Response<RandomPod>
        try {
            randomPodcast = podcastNetwork.justListen()

            if (randomPodcast.isSuccessful){
                randomPodcast.body()?.let {
                    return responseHandler.handleSuccess(it)
                }
                return  responseHandler.handleDefaultException()

            }else{
                return responseHandler.handleDefaultException()
            }

        }catch (e : Exception){
            return responseHandler.handleException(e)
        }
    }


    suspend fun getGenre():Resource<PodcastGenre>{
        val currentGenre : Response<PodcastGenre>
        try {
            currentGenre = podcastNetwork.getGenre()
            if (currentGenre.isSuccessful){
                currentGenre.body()?.let {
                    i("repositor", "${it}")
                    return responseHandler.handleSuccess(it)
                }
                return responseHandler.handleDefaultException()
            }else{
                return responseHandler.handleDefaultException()
            }
        }catch (e : Exception){
            return responseHandler.handleException(e)
        }
    }

}