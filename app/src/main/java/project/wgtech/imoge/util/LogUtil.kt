package project.wgtech.imoge.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import project.wgtech.imoge.BuildConfig
import project.wgtech.imoge.common.CommonEnumeration
import project.wgtech.imoge.explore.view.ExploreFragment

class LogUtil () {

    companion object {
        private val debug = BuildConfig.DEBUG

        fun v (message: String) {
            if (debug) Log.v(CommonEnumeration.TAG.stringify, message)
        }
        fun d (message: String) {
            if (debug) Log.d(CommonEnumeration.TAG.stringify, message)
        }
        fun i (message: String) {
            if (debug) Log.i(CommonEnumeration.TAG.stringify, message)
        }
        fun w (enum: CommonEnumeration, message: String) = Log.w(CommonEnumeration.TAG.stringify, message)
        fun e (enum: CommonEnumeration, message: String) = Log.e(CommonEnumeration.TAG.stringify, message)

        fun toast (context: Context, message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    }
}