package com.salmanseifian.android.architecture.sample.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.salmanseifian.android.architecture.sample.data.model.Item
import com.salmanseifian.android.architecture.sample.databinding.ItemRepositoryBinding

class RepositoryViewHolder(private val binding: ItemRepositoryBinding) :
    BaseViewHolder<Item>(binding.root) {

    override fun bind(data: Item) {
        binding.repository = data

        if (data.hasWiki == true) {
            binding.root.setBackgroundColor(Color.LTGRAY)
        } else {
            binding.root.setBackgroundColor(Color.WHITE)
        }

    }

    companion object {
        fun create(parent: ViewGroup): RepositoryViewHolder {
            return RepositoryViewHolder(
                ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

}