package com.example.ui.podcast_genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.genre.Genre
import com.example.podcasts.databinding.GenreItemBinding

class GenreAdapter:RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    var data: List<Genre?> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreAdapter.ViewHolder {
        return ViewHolder(GenreItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: GenreAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount()= data.size

    inner class ViewHolder(private val binding: GenreItemBinding):RecyclerView.ViewHolder(binding.root){
        private  var currentItem: Genre? = null

        fun bind(){
            currentItem = data[adapterPosition]
            binding.textView.text = currentItem?.name
        }

    }

}