package com.rudachenkoroman.astonIntensivFinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.model.news.Article

class NewsAdapterSearch: RecyclerView.Adapter<NewsAdapterSearch.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private lateinit var articleImageSearch : ImageView
    private lateinit var articleChannelSearch : ImageView
    private lateinit var articleTitleSearch : TextView
    private lateinit var articleDescriptionSearch : TextView

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news_search,parent,false)
        )
    }

    val  differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val article = differ.currentList[position]
        articleImageSearch = holder.itemView.findViewById(R.id.articleImageSearch)
        articleChannelSearch = holder.itemView.findViewById(R.id.articleChannelSearch)
        articleTitleSearch = holder.itemView.findViewById(R.id.articleTitleSearch)
        articleDescriptionSearch = holder.itemView.findViewById(R.id.articleDescriptionSearch)

        with(article){
            Glide.with(holder.itemView.context)
                .load(this.urlToImage)
                .placeholder(R.drawable.placeholder_news_item)
                .into(articleImageSearch)
            articleTitleSearch.text = article.source.name
            articleDescriptionSearch.text = article.title

            holder.itemView.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(this)
                }
            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnclickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}