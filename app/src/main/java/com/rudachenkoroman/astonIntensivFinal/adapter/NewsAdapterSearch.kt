package com.rudachenkoroman.astonIntensivFinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.databinding.ItemNewsSearchBinding
import com.rudachenkoroman.astonIntensivFinal.model.news.Article

class NewsAdapterSearch(private val onClickSearch: (item: Article) -> Unit) : ListAdapter<Article, NewsAdapterSearch.ArticleViewHolder>(UserDiffUtil) {

    inner class ArticleViewHolder(private val binding: ItemNewsSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            Glide.with(itemView.context)
                .load(article.urlToImage)
                .placeholder(R.drawable.placeholder_news_item)
                .into(binding.articleImageSearch)
            binding.articleTitleSearch.text = article.source.name
            binding.articleDescriptionSearch.text = article.title
        }
    }

    object UserDiffUtil : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsSearchBinding.inflate(inflater,parent,false)
        val viewHolder = ArticleViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val item = getItem(viewHolder.adapterPosition)
            onClickSearch(item)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}