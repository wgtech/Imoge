package project.wgtech.imoge.common

import android.app.Application
import android.content.Intent
import android.content.IntentFilter

class GlobalApplication : Application() {

    private val broadcastReceiver = CommonBroadcastReceiver()

    override fun onCreate() {
        super.onCreate()

        val intentFilter = IntentFilter()
        intentFilter.apply {
            this.addAction(Intent.ACTION_BOOT_COMPLETED)
        }
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onLowMemory() {
        unregisterReceiver(broadcastReceiver)
        super.onLowMemory()
    }

    override fun onTerminate() {
        unregisterReceiver(broadcastReceiver)
        super.onTerminate()
    }
}