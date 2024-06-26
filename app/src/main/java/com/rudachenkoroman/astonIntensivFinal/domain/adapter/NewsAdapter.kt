package com.rudachenkoroman.astonIntensivFinal.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.databinding.ItemNewsBinding
import com.rudachenkoroman.astonIntensivFinal.domain.model.news.Article
import com.rudachenkoroman.astonIntensivFinal.presentation.util.getImageSourceNews

class NewsAdapter(private val onClickNews: (item: Article) -> Unit) : ListAdapter <Article, NewsAdapter.ArticleViewHolder>(
    UserDiffUtil
) {

    inner class ArticleViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(article.urlToImage)
                    .placeholder(R.drawable.placeholder_news_item)
                    .into(articleImage)
                articleImageSource.setImageResource(getImageSourceNews(article))
                articleTitle.text = article.source.name
                articleDescription.text = article.title
            }
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
        val binding = ItemNewsBinding.inflate(inflater,parent,false)
        val viewHolder = ArticleViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val item = getItem(viewHolder.adapterPosition)
            onClickNews(item)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}