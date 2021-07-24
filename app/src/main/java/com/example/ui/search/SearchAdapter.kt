package com.example.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.podcasts.databinding.SpecificPodcastItemBinding
import com.example.model.search.Result

class SearchAdapter:RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var data: List<Result> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        return ViewHolder(SpecificPodcastItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount()=data.size

    inner class ViewHolder(private val binding: SpecificPodcastItemBinding):RecyclerView.ViewHolder(binding.root){
        private lateinit var currentItem: Result
        fun bind(){
                currentItem = data[adapterPosition]
                binding.podcastNameTxt.text = currentItem.titleOriginal
                binding.podcastTitleTxt.text = currentItem.email
            Glide.with(itemView.context).load(currentItem.image)
                .into(binding.podcastImg)


        }
    }
}