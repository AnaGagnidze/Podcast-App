package com.example.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.model.Podcasts
import com.example.podcasts.databinding.PopularItemBinding
import com.example.podcasts.databinding.SimilarPodcastBinding

typealias ImgClick = (id: String?) -> Unit
class HomeAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var imgClick: ImgClick
    lateinit var secondImgClick: ImgClick

    var data: List<Podcasts> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var type: Int? = null


    override fun getItemViewType(position: Int): Int {
        return type!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(type){
            1 -> {
                PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            else ->{
                SimilarViewHolder(SimilarPodcastBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PopularViewHolder ->{
                holder.bind()
            }
            is SimilarViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemCount()= data.size

    inner class PopularViewHolder(private var binding: PopularItemBinding):RecyclerView.ViewHolder(binding.root),View.OnClickListener{
        private lateinit var currentData: Podcasts
        fun bind(){
            binding.root.setOnClickListener(this)
            currentData = data[adapterPosition]
            binding.authorTxt.text = currentData.title
            binding.nameTxt.text = currentData.publisher
            Glide.with(itemView.context).load(currentData.image).into(binding.podcastImg)

        }

        override fun onClick(v: View?) {
            secondImgClick(currentData.id)
        }
    }

    inner class SimilarViewHolder(private val binding: SimilarPodcastBinding):RecyclerView.ViewHolder(binding.root),View.OnClickListener{
        private lateinit var currentData: Podcasts
        fun bind(){
            binding.root.setOnClickListener(this)
            currentData = data[adapterPosition]
            binding.podcastNameTxt.text = currentData.title
            binding.podcastText.text = currentData.publisher
            Glide.with(itemView.context).load(currentData.image).into(binding.podcastImg)

        }

        override fun onClick(v: View?) {
            imgClick(currentData.id)
        }
    }


}