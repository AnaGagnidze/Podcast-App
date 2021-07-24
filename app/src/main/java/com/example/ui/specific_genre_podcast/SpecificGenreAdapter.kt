package com.example.ui.specific_genre_podcast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.model.Podcasts
import com.example.podcasts.databinding.SpecificPodcastItemBinding

typealias PodcastClick =(id: String) -> Unit
class SpecificAdapter:RecyclerView.Adapter<SpecificAdapter.ViewHolder>() {


    lateinit var podcastClick: PodcastClick

    var data: List<Podcasts> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificAdapter.ViewHolder {
        return ViewHolder(SpecificPodcastItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SpecificAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount()=data.size

    inner class ViewHolder(private val binding: SpecificPodcastItemBinding):RecyclerView.ViewHolder(binding.root),View.OnClickListener{
        private lateinit var currentItem : Podcasts
        fun bind(){
            currentItem = data[adapterPosition]
            binding.root.setOnClickListener(this)

            binding.podcastNameTxt.text = currentItem.title
            binding.podcastTitleTxt.text = currentItem.publisher

            Glide.with(itemView.context).load(currentItem.image)
                .into(binding.podcastImg)




        }

        override fun onClick(v: View?) {
            currentItem.id?.let { podcastClick(it) }
        }
    }


}