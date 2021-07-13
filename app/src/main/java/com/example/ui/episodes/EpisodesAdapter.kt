package com.example.ui.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.model.specificPodcast.Episode
import com.example.podcasts.databinding.EpisodesItemBinding

class EpisodesAdapter:RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    var data: List<Episode?> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesAdapter.ViewHolder {
        return ViewHolder(
            EpisodesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: EpisodesAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount()=data.size

    inner class ViewHolder(private val binding: EpisodesItemBinding):RecyclerView.ViewHolder(binding.root){
        private  var currentItem: Episode? = null

        fun bind(){
            currentItem = data[adapterPosition]
            binding.podcastNameTxt.text = currentItem?.title
            binding.podcastTitleTxt.text = currentItem?.description

            Glide.with(itemView.context).load(currentItem?.image)
                .into(binding.podcastImg)
        }
    }

}