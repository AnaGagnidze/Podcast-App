package com.example.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.model.favorites.FavoritePodcast
import com.example.podcasts.databinding.SpecificPodcastItemBinding

typealias PodcastClick =(id: String) -> Unit
class ProfileAdapter: RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

     lateinit var podcastClick: PodcastClick

    var data: List<FavoritePodcast> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ViewHolder {
        return ViewHolder(SpecificPodcastItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: ProfileAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount()=data.size

    inner class ViewHolder(private val binding: SpecificPodcastItemBinding):RecyclerView.ViewHolder(binding.root),View.OnClickListener{
        private lateinit var current: FavoritePodcast

        fun bind(){

            binding.root.setOnClickListener(this)
            current = data[adapterPosition]

            binding.podcastTitleTxt.text = current.description
            binding.podcastNameTxt.text = current.title

            Glide.with(itemView.context).load(current.img)
                .into(binding.podcastImg)

        }

        override fun onClick(v: View?) {
            podcastClick(current.podcastId)
        }


    }

}