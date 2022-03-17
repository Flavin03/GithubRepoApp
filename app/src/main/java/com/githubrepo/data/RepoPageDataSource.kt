package com.githubrepo.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.githubrepo.network.NetworkState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.githubrepo.data.Result
import com.githubrepo.network.RepoRemoteDataSource
import com.githubrepo.ui.MainViewModel
import com.githubrepo.utils.Constants
import javax.inject.Inject

class RepoPageDataSource  @Inject constructor(
    private val remoteDataSource: RepoRemoteDataSource,
    private val githubDao: GithubDao,
    private val coroutineScope: CoroutineScope
) : PageKeyedDataSource<Int, GithubEntity>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GithubEntity>
    ) {
        networkState.postValue(NetworkState.LOADING)
        fetchData(page = 1, pageSize = params.requestedLoadSize.toLong()) {
            callback.onResult(it, null, 2)
        }
    }
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GithubEntity>) {
        networkState.postValue(NetworkState.LOADING)
        val page = params.key
        fetchData(page = page, pageSize = params.requestedLoadSize.toLong()) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GithubEntity>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize.toLong()) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Long, callback: (List<GithubEntity>) -> Unit) {
        coroutineScope.launch(getJobErrorHandler()) {
            when (val response = remoteDataSource.fetchRepoList(
                "", "",pageSize)) {
                is Result.Error -> {
                    networkState.postValue(NetworkState.ERROR(response.message ?: "Unknown error"))
                    postError(response.message)
                }
                is Result.Success -> {
                    val results = response.data.items
                    githubDao.insertRepositories(results)
                    callback(results)
                    networkState.postValue(NetworkState.LOADED)
                }
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String?) {
        Log.e("NewsPageDataSource","An error happened: $message")
    }
}
