package com.githubrepo.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.githubrepo.data.GithubDao
import com.githubrepo.data.GithubEntity
import com.githubrepo.data.RepoPageDataSourceFactory
import com.githubrepo.network.Data
import com.githubrepo.network.NetworkState
import com.githubrepo.network.RepoRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(
    private val githubDao: GithubDao,
    private val repoRemoteDataSource: RepoRemoteDataSource){

    fun observePagedData(connectivityAvailable : Boolean, coroutineScope: CoroutineScope)
            : Data<GithubEntity> {

        return if (connectivityAvailable)
            observeRemotePagedData(coroutineScope)
        else observeLocalPagedData()
    }
    private fun observeLocalPagedData(): Data<GithubEntity> {

        val dataSourceFactory = githubDao.getPagedRepoData()

        val createLD = MutableLiveData<NetworkState>()
        createLD.postValue(NetworkState.LOADED)

        return Data(
            LivePagedListBuilder(dataSourceFactory,
            RepoPageDataSourceFactory.pagedListConfig()).build(),createLD)
    }

    private fun observeRemotePagedData(ioCoroutineScope: CoroutineScope): Data<GithubEntity> {
        val dataSourceFactory = RepoPageDataSourceFactory(repoRemoteDataSource,
            githubDao, ioCoroutineScope)

        val networkState = Transformations.switchMap(dataSourceFactory.liveData) {
            it.networkState
        }
        return Data(LivePagedListBuilder(dataSourceFactory,
            RepoPageDataSourceFactory.pagedListConfig()).build(),networkState)
    }

    fun syncRepoData(connectivityAvailable : Boolean, coroutineScope: CoroutineScope){
        if (connectivityAvailable)
            observeRemotePagedData(coroutineScope)
    }

}