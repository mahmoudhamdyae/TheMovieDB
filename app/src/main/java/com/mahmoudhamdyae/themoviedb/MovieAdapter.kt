package com.mahmoudhamdyae.themoviedb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.themoviedb.databinding.MovieViewItemBinding
import com.mahmoudhamdyae.themoviedb.database.network.Movie

class MovieAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Movie, MovieAdapter.MoviePropertyViewHolder>(DiffCallback) {

    /**
     * The MoviePropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Movie] information.
     */
    class MoviePropertyViewHolder(private var binding: MovieViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.property = movie
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Movie]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MoviePropertyViewHolder {
        return MoviePropertyViewHolder(MovieViewItemBinding.inflate(LayoutInflater.from(parent.context)))
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
    * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Movie]
    * associated with the current item to the [onClick] function.
    * @param clickListener lambda that will be called with the current [Movie]
    */
    class OnClickListener(val clickListener: (movie:Movie) -> Unit) {
        fun onClick(movie:Movie) = clickListener(movie)
    }
}