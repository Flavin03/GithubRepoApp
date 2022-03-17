package com.githubrepo

import android.app.Activity
import android.app.Application
import androidx.work.*
import com.githubrepo.di.AppComponent
import com.githubrepo.di.DaggerAppComponent
import com.githubrepo.worker.SyncWorker
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MyApplication : Application(), HasAndroidInjector {

    @Inject
    public lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    private fun workManagerJob(){
        val workManagerConfiguration = Configuration.Builder()
            .setWorkerFactory(SyncWorker.Factory())
            .build()

        WorkManager.initialize(this, workManagerConfiguration)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val work = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(SyncWorker::class.java.name, ExistingPeriodicWorkPolicy.KEEP, work)
    }

}