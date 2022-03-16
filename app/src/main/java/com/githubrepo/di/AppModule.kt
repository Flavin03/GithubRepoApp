package com.githubrepo.di

import android.content.Context
import com.githubrepo.MyApplication
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
class AppModule{

    @Provides
    fun provideContext(application: MyApplication): Context = application

    @Provides
    @CoroutineScopeIO
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)
}