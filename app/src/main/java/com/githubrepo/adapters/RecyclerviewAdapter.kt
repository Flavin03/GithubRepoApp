package com.githubrepo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.githubrepo.data.GithubEntity
import com.githubrepo.databinding.ListItemBinding

class RecyclerviewAdapter() :
    PagedListAdapter<GithubEntity, RecyclerviewAdapter.ViewHolder>(DiffCallback()) {
    private var clickListener: ClickListener? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repoItem = getItem(position)
        holder.apply {
            bind(createOnClickListener(repoItem!!), repoItem)
            itemView.tag = repoItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun createOnClickListener(githubEntity: GithubEntity): View.OnClickListener {
        return View.OnClickListener {
            clickListener?.goToDetailScreen(githubEntity)
        }
    }

    class ViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: GithubEntity?) {
            binding.apply {
                clickListener = listener
                repo = item
                owner = item?.owner
                executePendingBindings()
            }
        }
    }

    interface ClickListener{
        fun goToDetailScreen(githubEntity: GithubEntity)
    }

    fun setClickListener(clickListener: ClickListener){
           this.clickListener = clickListener
    }
}

private class DiffCallback : DiffUtil.ItemCallback<GithubEntity>() {

    override fun areItemsTheSame(oldItem: GithubEntity, newItem: GithubEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GithubEntity, newItem: GithubEntity): Boolean {
        return oldItem == newItem
    }
}