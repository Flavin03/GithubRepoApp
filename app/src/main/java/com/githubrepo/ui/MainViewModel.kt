package com.githubrepo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.githubrepo.data.GithubEntity
import com.githubrepo.di.CoroutineScopeIO
import com.githubrepo.network.Data
import com.githubrepo.repository.GithubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: GithubRepository,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope) : ViewModel() {

    var dataList: Data<GithubEntity>? = null

    fun getRepoList(connectivityAvailable: Boolean): Data<GithubEntity>? {

        if (dataList == null) {
            dataList = repository.observePagedData(connectivityAvailable, ioCoroutineScope)
        }
        return dataList
    }

    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }

}