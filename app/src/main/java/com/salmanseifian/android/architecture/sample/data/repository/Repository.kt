package com.salmanseifian.android.architecture.sample.data.repository

import androidx.paging.PagingData
import com.salmanseifian.android.architecture.sample.data.model.Item
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun searchRepositories(q: String, page: Int): Flow<PagingData<Item>>
}