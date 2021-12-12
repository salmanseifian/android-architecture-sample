package com.salmanseifian.android.architecture.sample.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.salmanseifian.android.architecture.sample.data.model.Item
import com.salmanseifian.android.architecture.sample.data.remote.ApiService
import com.salmanseifian.android.architecture.sample.data.remote.GithubRepositoryDataSource
import com.salmanseifian.android.architecture.sample.utils.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val apiService: ApiService) : Repository {

    override fun searchRepositories(q: String): Flow<PagingData<Item>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = NETWORK_PAGE_SIZE,
            ),
            pagingSourceFactory = {
                GithubRepositoryDataSource(apiService, q)
            }
        ).flow
    }
}