package com.example.ui.podcast_genre

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.base.BaseFragment
import com.example.model.genre.Genre
import com.example.podcasts.R
import com.example.podcasts.databinding.GenrePodcastFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenrePodcastFragment : BaseFragment<GenrePodcastFragmentBinding>(GenrePodcastFragmentBinding::inflate) {

    private lateinit var adapter: GenreAdapter

    private val viewModel: GenrePodcastViewModel by viewModels()

    override fun setUpFragment() {
        bindRec()
    }

    private fun openNewFragment(genre:Genre){

        setFragmentResult("requestKey", bundleOf("bundleKey" to genre))
        findNavController().navigate(R.id.action_genrePodcastFragment_to_specificPodcastFragment)
    }


    private fun bindRec(){
        adapter = GenreAdapter()
        binding.genreRecyclerview.layoutManager = GridLayoutManager(requireContext(),1)
        binding.genreRecyclerview.adapter = adapter
        viewModel.load()

        adapter.genreClick ={
            openNewFragment(it)
        }

        viewModel.genres.observe(viewLifecycleOwner){

            adapter.data = it
        }



    }


}