package com.githubrepo.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.githubrepo.ui.MainViewModel

@Dao
interface GithubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(githubEntities: List<GithubEntity?>?): LongArray?

    @Query("SELECT * FROM `GithubEntity` where page = :page")
    fun getRepositoriesByPage(page: Long?): List<GithubEntity?>?

    @Query("SELECT * FROM GithubEntity")
    fun getPagedNews(): DataSource.Factory<Int, GithubEntity>
}