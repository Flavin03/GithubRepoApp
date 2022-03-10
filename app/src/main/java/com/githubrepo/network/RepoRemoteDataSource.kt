package com.githubrepo.network

import com.githubrepo.model.GithubApiResponse
import com.githubrepo.utils.Constants
import com.githubrepo.data.Result
import javax.inject.Inject

class RepoRemoteDataSource  @Inject constructor(private val apiInterface: ApiInterface) : BaseDataSource() {

    suspend fun fetchRepoList(sort : String, order : String, pageSize : Long ) : Result<GithubApiResponse> {
        return getResult { apiInterface.getRepoList( Constants.QUERY_API, Constants.PAGE_MAX_SIZE,
            sort,order,pageSize) }
    }
}
