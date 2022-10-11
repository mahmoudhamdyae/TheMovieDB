package com.mahmoudhamdyae.themoviedb.detail.trailers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.themoviedb.databinding.TrailerViewItemBinding
import com.mahmoudhamdyae.themoviedb.domain.Movie
import com.mahmoudhamdyae.themoviedb.network.NetworkTrailer


class TrailersAdapter(val onClickListener: OnClickListener) :
    ListAdapter<NetworkTrailer, TrailersAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val networkTrailer = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(networkTrailer)
        }
        holder.bind(networkTrailer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: TrailerViewItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(trailer: NetworkTrailer) {
            binding.property = trailer
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TrailerViewItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [NetworkTrailer]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Movie]
     */
    class OnClickListener(val clickListener: (networkTrailer: NetworkTrailer) -> Unit) {
        fun onClick(networkTrailer: NetworkTrailer) = clickListener(networkTrailer)
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class DiffCallback : DiffUtil.ItemCallback<NetworkTrailer>() {
    override fun areItemsTheSame(oldItem: NetworkTrailer, newItem: NetworkTrailer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NetworkTrailer, newItem: NetworkTrailer): Boolean {
        return oldItem.id == newItem.id
    }
}