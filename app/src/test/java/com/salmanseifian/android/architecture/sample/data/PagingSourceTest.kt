package com.salmanseifian.android.architecture.sample.data

import androidx.paging.PagingSource
import com.salmanseifian.android.architecture.sample.data.model.Item
import com.salmanseifian.android.architecture.sample.data.model.SearchRepositoriesResponse
import com.salmanseifian.android.architecture.sample.data.remote.ApiService
import com.salmanseifian.android.architecture.sample.data.remote.GithubRepositoryDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class PagingSourceTest {


    @Mock
    lateinit var apiService: ApiService

    lateinit var githubRepositoryDataSource: GithubRepositoryDataSource

    val q = "tetris"


    companion object {
        val searchRepositoryResponse = SearchRepositoriesResponse(false, listOf(), 46558)
    }

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        githubRepositoryDataSource = GithubRepositoryDataSource(apiService, q)
    }


    @Test
    fun `repositories paging source load - failure - http error`() = runBlockingTest {
        val error = RuntimeException("404", Throwable())
        given(apiService.searchRepositories(any(), any())).willThrow(error)

        val expectedResult = PagingSource.LoadResult.Error<Int, Item>(error)

        assertEquals(
            expectedResult,
            githubRepositoryDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }


    @Test
    fun `repositories paging source load - failure - received null`() = runBlockingTest {
        given(apiService.searchRepositories(any(), any())).willReturn(null)

        val expectedResult = PagingSource.LoadResult.Error<Int, Item>(NullPointerException())

        assertEquals(
            expectedResult.toString(),
            githubRepositoryDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )


    }
}