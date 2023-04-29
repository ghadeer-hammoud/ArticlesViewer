package com.ghadeer.articlesviewer.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ghadeer.articlesviewer.R
import com.ghadeer.articlesviewer.data.models.Article
import com.ghadeer.articlesviewer.databinding.ArticleListRowBinding
import com.ghadeer.articlesviewer.utils.formatDate
import com.ghadeer.articlesviewer.utils.loadImage


class ArticlesRecyclerAdapter(private val itemsList: MutableList<Article>,
                            private val onArticleClickListener: OnArticleClickListener?)
    : RecyclerView.Adapter<ArticlesRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ArticleListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItemsList: List<Article>){

        val diffCallback = ArticlesDiffCallback(this.itemsList, newItemsList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        itemsList.clear()
        itemsList.addAll(newItemsList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val itemBinding: ArticleListRowBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: Article){

            itemBinding.apply {

                if(item.media.isNotEmpty()){
                    if(item.media.first().metadata.isNotEmpty())
                        ivImage.loadImage(item.media.first().metadata.last().url)
                }
                tvTitle.text = item.title
                tvByline.text = item.byline
                tvPublishedDate.text = item.publishedDate.formatDate()
                root.setOnClickListener { onArticleClickListener?.onArticleClicked(item.id) }
            }

        }
    }
}

interface OnArticleClickListener{
    fun onArticleClicked(articleId: Long)
}

class ArticlesDiffCallback(
    private val oldList: List<Article>,
    private val newList: List<Article>)
    : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.title == newItem.title &&
                oldItem.byline == newItem.byline &&
                oldItem.publishedDate == newItem.publishedDate
    }
}