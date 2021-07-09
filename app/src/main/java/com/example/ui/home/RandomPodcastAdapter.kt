package com.example.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.model.random_podcast.RandomPod
import com.example.podcasts.databinding.RandomItemBinding

class RandomPodcastAdapter:RecyclerView.Adapter<RandomPodcastAdapter.Viewholder>() {

    var data: List<RandomPod?> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RandomPodcastAdapter.Viewholder {
        return Viewholder(RandomItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RandomPodcastAdapter.Viewholder, position: Int) {
        holder.bind()
    }

    override fun getItemCount()=data.size

    inner class Viewholder(private val binding: RandomItemBinding):RecyclerView.ViewHolder(binding.root){
        private lateinit var currentData: RandomPod

        fun bind(){
            currentData = data[adapterPosition] ?: return
            Glide.with(itemView.context).load(currentData.thumbnail).into(binding.podcastImg)

        }
    }

}