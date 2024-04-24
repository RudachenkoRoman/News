package com.rudachenkoroman.astonIntensivFinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rudachenkoroman.astonIntensivFinal.databinding.ItemSourceBinding
import com.rudachenkoroman.astonIntensivFinal.model.source.Source
import com.rudachenkoroman.astonIntensivFinal.util.getImageSource

class SourceAdapter(private val onClickSource: (item: Source) -> Unit) : ListAdapter<Source, SourceAdapter.SourceViewHolder>(UserDiffUtil) {
    inner class SourceViewHolder(private val binding: ItemSourceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(source: Source) {
            binding.apply {
                sourceImage.setImageResource(getImageSource(source))
                sourceTitle.text = source.name
                sourceCategory.text = source.category.replace(" ", "")
                sourceCountry.text = source.country
            }
        }
    }

    object UserDiffUtil : DiffUtil.ItemCallback<Source>() {
        override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Source, newItem: Source): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: SourceAdapter.SourceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSourceBinding.inflate(inflater,parent,false)
        val viewHolder = SourceViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val item = getItem(viewHolder.adapterPosition)
            onClickSource(item)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}