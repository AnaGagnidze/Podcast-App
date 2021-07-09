package com.example.ui.home

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.base.BaseFragment
import com.example.podcasts.R
import com.example.podcasts.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import me.relex.circleindicator.CircleIndicator2


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {



    private lateinit var randomAdapter: RandomPodcastAdapter
    private lateinit var popularAdapter: HomeAdapter
    private lateinit var similarAdapter: HomeAdapter


    private val viewModel: HomeViewModel by viewModels()

    override fun setUpFragment() {
        setRec()
    }


    private fun dataObserv(){
        viewModel.podcasts.observe(viewLifecycleOwner){
            popularAdapter.type = 1
            popularAdapter.data = it

        }
        viewModel.randomPodcasts.observe(viewLifecycleOwner){
            randomAdapter.data = it
        }
        viewModel.similarPod.observe(viewLifecycleOwner){
            similarAdapter.type = 3
            similarAdapter.data = it
        }
    }

    private fun setCircleIndicator(rec: RecyclerView, adapter: RandomPodcastAdapter){

        val indicator: CircleIndicator2 = binding.indicator
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(rec)
        indicator.attachToRecyclerView(rec,pagerSnapHelper)
        adapter.registerAdapterDataObserver(indicator.adapterDataObserver)
    }

    private fun setRec(){
        randomAdapter = RandomPodcastAdapter()
        binding.randomRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.randomRecyclerview.adapter = randomAdapter
        setCircleIndicator(binding.randomRecyclerview,randomAdapter)


        popularAdapter = HomeAdapter()
        binding.popularRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            binding.popularRecyclerView.adapter = popularAdapter

        similarAdapter = HomeAdapter()
            binding.similarPodcastRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            binding.similarPodcastRecyclerview.adapter = similarAdapter


        dataObserv()


    }


}