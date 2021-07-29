package com.example.ui.episodes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.extensions.cleanText
import com.example.model.specificPodcast.Episode
import com.example.podcasts.databinding.DetailEpisodeItemBinding
import com.example.podcasts.databinding.EpisodesItemBinding

typealias EpisodeClick =(id: Episode?) -> Unit
typealias SaveClick =(id: Episode?) -> Unit
class EpisodesAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     lateinit var episodeClick: EpisodeClick

    var data: List<Episode?> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0){
            0
        }else{
            1
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType != 0){
            ViewHolder(
                EpisodesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }else{
            DetailViewHolder(
                DetailEpisodeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DetailViewHolder){
            holder.bind()
        }else if (holder is ViewHolder){
            holder.bind()
        }
    }

    override fun getItemCount()=data.size

    inner class DetailViewHolder(private val binding: DetailEpisodeItemBinding):RecyclerView.ViewHolder(binding.root){
        private  var currentItem: Episode? = null
        fun bind(){
            currentItem = data[adapterPosition]
            binding.podcastNameTxt.text = currentItem?.title
            binding.desciptionTxt.text = currentItem?.description?.cleanText()
            Glide.with(itemView.context).load(currentItem?.image)
                .into(binding.imgView)
        }
    }

    inner class ViewHolder(private val binding: EpisodesItemBinding):RecyclerView.ViewHolder(binding.root),View.OnClickListener{
        private  var currentItem: Episode? = null

        fun bind(){
            currentItem = data[adapterPosition]
            binding.podcastNameTxt.text = currentItem?.title



            binding.podcastTitleTxt.text = currentItem?.description?.cleanText()

            binding.root.setOnClickListener(this)

            Glide.with(itemView.context).load(currentItem?.image)
                .into(binding.podcastImg)
        }

        override fun onClick(v: View?) {
            episodeClick(currentItem)
        }
    }

}