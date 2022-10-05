package com.mahmoudhamdyae.themoviedb.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.themoviedb.databinding.GridViewItemBinding
import com.mahmoudhamdyae.themoviedb.network.MovieProperty

class MoviesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<MovieProperty, MoviesAdapter.MoviePropertyViewHolder>(DiffCallback) {

    /**
     * The MoviePropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [MovieProperty] information.
     */
    class MoviePropertyViewHolder(private var binding: GridViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieProperty) {
            binding.property = movie
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [MovieProperty]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<MovieProperty>() {
        override fun areItemsTheSame(oldItem: MovieProperty, newItem: MovieProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MovieProperty, newItem: MovieProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MoviePropertyViewHolder {
        return MoviePropertyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
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
    * Custom listener that handles clicks on [RecyclerView] items.  Passes the [MovieProperty]
    * associated with the current item to the [onClick] function.
    * @param clickListener lambda that will be called with the current [MovieProperty]
    */
    class OnClickListener(val clickListener: (movie:MovieProperty) -> Unit) {
        fun onClick(movie:MovieProperty) = clickListener(movie)
    }

}