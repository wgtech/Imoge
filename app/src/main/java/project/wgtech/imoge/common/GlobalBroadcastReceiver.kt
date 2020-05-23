package project.wgtech.imoge.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class GlobalBroadcastReceiver : BroadcastReceiver() {
    private val tag = javaClass.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(tag, "onReceive: " + intent?.action)

        when (intent?.action) {
            Intent.ACTION_MY_PACKAGE_REPLACED -> Log.d(tag, "ACTION_MY_PACKAGE_REPLACED")
        }
    }
}