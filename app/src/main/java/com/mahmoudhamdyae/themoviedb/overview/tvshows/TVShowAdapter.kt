package com.mahmoudhamdyae.themoviedb.overview.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.themoviedb.database.network.Movie
import com.mahmoudhamdyae.themoviedb.databinding.TvShowViewItemBinding

class TVShowAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Movie, TVShowAdapter.MoviePropertyViewHolder>(DiffCallback) {

    class MoviePropertyViewHolder(private var binding: TvShowViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: Movie) {
            binding.property = tvShow
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MoviePropertyViewHolder {
        return MoviePropertyViewHolder(TvShowViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MoviePropertyViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movie)
        }
        holder.bind(movie)
    }

    class OnClickListener(val clickListener: (tvShow:Movie) -> Unit) {
        fun onClick(tvShow:Movie) = clickListener(tvShow)
    }

}