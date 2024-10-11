package com.example.p57_news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.p57_news.R
import com.example.p57_news.databinding.AdapterItemBinding
import com.example.p57_news.model.Article
import com.example.p57_news.util.ClickHelper
import com.example.p57_news.viewmodel.MainViewModel

class ArticleAdapter(
    private val clickHelper: ClickHelper,
    private val viewModel: MainViewModel? = null,
) :
    ListAdapter<Article, ArticleAdapter.ViewHolder>(Comparator()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = AdapterItemBinding.bind(itemView)

        fun bindData(model: Article) = with(binding) {
            val publishedAt = model.publishedAt
            val finalPublishedAt =
                publishedAt.substring(0, publishedAt.length - 4).replace("T", " ")

            tvTitle.text = model.title
            tvDescription.text = model.description
            tvSource.text = model.source.name
            tvPublishedAt.text = finalPublishedAt
            Glide.with(root).load(model.urlToImage).into(ivImage)

            itemView.setOnClickListener {
                clickHelper.onClick(model)
            }
        }

    }

    class Comparator : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.adapter_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindData(item)
    }

    fun deleteItem(position: Int) {
        val list = currentList.toMutableList()
        val currentItem = list[position]
        list.removeAt(position)
        viewModel?.deleteArticle(currentItem)
        submitList(list)
    }

}