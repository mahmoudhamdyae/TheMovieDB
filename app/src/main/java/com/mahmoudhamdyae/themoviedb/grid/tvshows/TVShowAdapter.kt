package com.mahmoudhamdyae.themoviedb.grid.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.themoviedb.databinding.TvShowViewItemBinding
import com.mahmoudhamdyae.themoviedb.domain.TVShow

class TVShowAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<TVShow, TVShowAdapter.MoviePropertyViewHolder>(DiffCallback) {

    /**
     * The MoviePropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [TVShow] information.
     */
    class MoviePropertyViewHolder(private var binding: TvShowViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TVShow) {
            binding.property = tvShow
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [TVShow]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<TVShow>() {
        override fun areItemsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MoviePropertyViewHolder {
        return MoviePropertyViewHolder(TvShowViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MoviePropertyViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movie)
        }
        holder.bind(movie)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [TVShow]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [TVShow]
     */
    class OnClickListener(val clickListener: (tvShow:TVShow) -> Unit) {
        fun onClick(tvShow:TVShow) = clickListener(tvShow)
    }

}