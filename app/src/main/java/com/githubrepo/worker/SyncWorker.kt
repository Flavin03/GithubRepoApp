package com.githubrepo.worker

import android.content.Context
import android.content.ContextParams
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import java.lang.Error

class SyncWorker(context: Context, params: WorkerParameters):
    CoroutineWorker(context, params){

    override suspend fun doWork(): Result {
        return try{
            Result.success()
        }catch (error: Error){
            Result.failure()
        }
    }

    class Factory(): WorkerFactory(){
        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
        ): ListenableWorker? {
         return SyncWorker(appContext,workerParameters)
        }

    }

}