package com.example.ui.podcast_genre

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.base.BaseFragment
import com.example.podcasts.databinding.GenrePodcastFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenrePodcastFragment : BaseFragment<GenrePodcastFragmentBinding>(GenrePodcastFragmentBinding::inflate) {

    private lateinit var adapter: GenreAdapter

    private val viewModel: GenrePodcastViewModel by viewModels()

    override fun setUpFragment() {
        bindRec()
    }


    private fun bindRec(){
        adapter = GenreAdapter()
        binding.genreRecyclerview.layoutManager = GridLayoutManager(requireContext(),1)
        binding.genreRecyclerview.adapter = adapter
        viewModel.load()
        viewModel.genres.observe(viewLifecycleOwner){

            adapter.data = it
        }



    }


}