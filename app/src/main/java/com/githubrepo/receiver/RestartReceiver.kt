package com.githubrepo.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.githubrepo.utils.Constants

class RestartReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val  isRestarted = intent?.getBooleanExtra(Constants.STATE, false) ?: return

        if(isRestarted){

        }
    }

}