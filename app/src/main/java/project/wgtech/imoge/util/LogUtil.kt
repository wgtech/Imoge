package project.wgtech.imoge.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import project.wgtech.imoge.BuildConfig

class LogUtil () {

    companion object {
        private val debug = BuildConfig.DEBUG

        fun v (types: Types, message: String) {
            if (debug) Log.v(types.tag, message)
        }
        fun d (types: Types, message: String) {
            if (debug) Log.d(types.tag, message)
        }
        fun i (types: Types, message: String) {
            if (debug) Log.i(types.tag, message)
        }
        fun w (types: Types, message: String) = Log.w(types.tag, message)
        fun e (types: Types, message: String) = Log.e(types.tag, message)

        fun toast (context: Context, message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    }

}