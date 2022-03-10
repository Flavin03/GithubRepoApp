package com.githubrepo

import android.app.Activity
import android.app.Application
import com.githubrepo.di.AppComponent
import com.githubrepo.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = initDaggerComponent()
    }

    fun getMyComponent(): AppComponent {
        return appComponent
    }

    fun initDaggerComponent(): AppComponent {
        appComponent = DaggerAppComponent
            .builder().application(this)
            .build()
        return appComponent
    }
}