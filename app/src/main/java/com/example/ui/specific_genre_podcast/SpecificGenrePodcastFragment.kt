package com.example.ui.specific_genre_podcast

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.BaseFragment
import com.example.model.genre.Genre
import com.example.podcasts.R
import com.example.podcasts.databinding.SpecificPodcastFragmentBinding
import com.example.util.usecases.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpecificPodcastFragment : BaseFragment<SpecificPodcastFragmentBinding>(SpecificPodcastFragmentBinding::inflate) {

    private val viewModel: SpecificPodcastViewModel by viewModels()

    private lateinit var adapter: SpecificAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("requestKey"){requestKey, bundle ->
            val genre = bundle.getParcelable<Genre>("bundleKey")
            genre?.id?.let { viewModel.load(it) }
            genre?.name?.let { binding.genreTxt.text = it }
        }

    }



    override fun setUpFragment() {
        setRecycler()

        binding.refresh.setOnRefreshListener {
            setRecycler()
        }

    }


    private fun setRecycler(){

        adapter = SpecificAdapter()
        binding.specificPodcastFragmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.specificPodcastFragmentRecyclerView.adapter = adapter
        adapter.podcastClick = {

            goSpecificPodcastFragment(it)

        }
        viewModel.podcasts.observe(viewLifecycleOwner){
            binding.refresh.isRefreshing = it.loading
            when (it.status){
                Status.SUCCESS -> {
                    adapter.data = it.data!!.podcasts!!
                }
                Status.ERROR -> {}
                Status.LOADING -> {}
            }
        }

    }

    private fun goSpecificPodcastFragment(id:String){

            setFragmentResult("podcastKey", bundleOf("podcastId" to id))
            findNavController().navigate(R.id.action_specificPodcastFragment_to_episodeFragment)

    }


}