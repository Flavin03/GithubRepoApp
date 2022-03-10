package com.githubrepo.di

import android.content.Context
import com.githubrepo.MyApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule{

    @Provides
    fun provideContext(application: MyApplication): Context = application
}