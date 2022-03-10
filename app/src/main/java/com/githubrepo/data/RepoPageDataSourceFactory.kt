package com.githubrepo.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.githubrepo.network.RepoRemoteDataSource
import com.githubrepo.ui.MainViewModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class RepoPageDataSourceFactory @Inject constructor(
    private val dataSource: RepoRemoteDataSource,
    private val dao: GithubDao,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, GithubEntity>() {

    val liveData = MutableLiveData<RepoPageDataSource>()

    override fun create(): DataSource<Int, GithubEntity> {
        val source = RepoPageDataSource(dataSource, dao, scope)
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 20
        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }
}
