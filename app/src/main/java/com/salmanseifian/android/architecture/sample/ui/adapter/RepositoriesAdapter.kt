package com.salmanseifian.android.architecture.sample.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.salmanseifian.android.architecture.sample.data.model.Item

class RepositoriesAdapter :
    PagingDataAdapter<Item, RepositoryViewHolder>(REPOSITORY_COMPARATOR) {

    companion object {
        private val REPOSITORY_COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        getItem(position)?.let { repository ->
            holder.bind(repository)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryViewHolder {
        return RepositoryViewHolder.create(parent)
    }

}