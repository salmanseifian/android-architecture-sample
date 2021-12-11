package com.salmanseifian.android.architecture.sample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salmanseifian.android.architecture.sample.data.model.Item
import com.salmanseifian.android.architecture.sample.databinding.ItemRepositoryBinding

class RepositoryViewHolder(private val binding: ItemRepositoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Item) {
        binding.repository = data
    }

    companion object {
        fun create(parent: ViewGroup): RepositoryViewHolder {
            return RepositoryViewHolder(
                ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

}