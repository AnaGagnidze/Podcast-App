package com.example.ui.search

import android.util.Log.i
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.BaseFragment
import com.example.podcasts.databinding.SearchFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.model.search.Result

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchFragmentBinding>(SearchFragmentBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()


    private lateinit var adapter: SearchAdapter


    override fun setUpFragment() {

        setUpRec()

        binding.tipSearchView.doOnTextChanged { text, start, before, count ->
            val len = text?.length ?: 0
            if (len > 3){
                i("shesvla", "shevita if count > 3 shi")
                viewModel.searchPodcast(text.toString())

            }



        }

        viewModel.podcast.observe(viewLifecycleOwner){
            i("shesvla", "shevita observshi")
            adapter.data = it

        }





    }

    private fun setUpRec(){
        adapter = SearchAdapter()
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecyclerView.adapter = adapter

    }


}