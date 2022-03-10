package com.githubrepo.di

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import com.githubrepo.data.AppDatabase
import com.githubrepo.data.GithubDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java, "Github.db"
        )
            .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideGithubDao(@NonNull appDatabase: AppDatabase): GithubDao {
        return appDatabase.githubDao()
    }
}
