package com.salmanseifian.android.architecture.sample.data.remote

import com.salmanseifian.android.architecture.sample.data.model.SearchRepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") q: String,
        @Query("page") page: Int
    ): SearchRepositoriesResponse
}