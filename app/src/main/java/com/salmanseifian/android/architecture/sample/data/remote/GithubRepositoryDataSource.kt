package com.salmanseifian.android.architecture.sample.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.salmanseifian.android.architecture.sample.data.model.Item
import com.salmanseifian.android.architecture.sample.utils.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class GithubRepositoryDataSource(private val apiService: ApiService, private val q: String) :
    PagingSource<Int, Item>() {


    override fun getRefreshKey(state: PagingState<Int, Item>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response =
                apiService.searchRepositories(q, position)
            val repositories = response.items
            LoadResult.Page(
                data = repositories,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position,
                nextKey = if (repositories.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}