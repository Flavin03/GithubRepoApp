package com.githubrepo.network

import com.githubrepo.model.GithubApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("search/repositories")
    suspend fun getRepoList(
        @Query("q") query: String,
        @Query("per_page") per_page: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("page") page: Long): Response<GithubApiResponse>
}