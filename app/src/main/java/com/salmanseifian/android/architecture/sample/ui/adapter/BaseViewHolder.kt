package com.salmanseifian.android.architecture.sample.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<model : Any>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(model: model)
}