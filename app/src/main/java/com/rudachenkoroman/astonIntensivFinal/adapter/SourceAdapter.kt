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
import com.rudachenkoroman.astonIntensivFinal.model.source.Source

class SourceAdapter: RecyclerView.Adapter<SourceAdapter.SourceViewHolder>() {

    inner class SourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private lateinit var sourceImage : ImageView
    private lateinit var sourceTitle : TextView
    private lateinit var sourceCategory: TextView
    private lateinit var sourceCountry: TextView

    private val differCallback = object : DiffUtil.ItemCallback<Source>() {
        override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Source, newItem: Source): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        return SourceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_source,parent,false)
        )
    }

    val  differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {

        val source = differ.currentList[position]
        sourceImage = holder.itemView.findViewById(R.id.sourceImage)
        sourceTitle = holder.itemView.findViewById(R.id.sourceTitle)
        sourceCategory = holder.itemView.findViewById(R.id.sourceCategory)
        sourceCountry = holder.itemView.findViewById(R.id.sourceCountry)

        with(source){
            Glide.with(holder.itemView.context)
                .load("https://media.istockphoto.com/id/1173300976/ru/%D0%B2%D0%B5%D0%BA%D1%82%D0%BE%D1%80%D0%BD%D0%B0%D1%8F/%D1%83%D0%BB%D1%8B%D0%B1%D0%BA%D0%B0-%D0%BB%D0%B8%D1%86%D0%BE-%D0%B7%D0%BD%D0%B0%D1%87%D0%BE%D0%BA-%D0%B8%D0%B7%D0%BE%D0%BB%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D1%8B-%D0%BD%D0%B0-%D0%B1%D0%B5%D0%BB%D0%BE%D0%BC-%D1%84%D0%BE%D0%BD%D0%B5-%D0%B2%D0%B5%D0%BA%D1%82%D0%BE%D1%80%D0%BD%D0%B0%D1%8F-%D0%B8%D0%BB%D0%BB%D1%8E%D1%81%D1%82%D1%80%D0%B0%D1%86%D0%B8%D1%8F.jpg?s=1024x1024&w=is&k=20&c=NdS0iiM-oL2O-hYEZob_M70_eLaoL_9X0BfVqXO-rZ8=")
                .placeholder(R.drawable.placeholder_news_item)
                .into(sourceImage)
            sourceTitle.text = source.name
            sourceCategory.text = source.category.replace(" ", "")
            sourceCountry.text = source.country


            holder.itemView.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(this)
                }
            }
        }
    }

    private var onItemClickListener: ((Source) -> Unit)? = null

    fun setOnclickListener(listener: (Source) -> Unit) {
        onItemClickListener = listener
    }
}