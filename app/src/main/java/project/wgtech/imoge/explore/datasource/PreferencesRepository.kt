package project.wgtech.imoge.explore.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import project.wgtech.imoge.common.CommonEnumeration

class PreferencesRepository(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(CommonEnumeration.SHARED_PREF.stringify, Context.MODE_PRIVATE)

    fun setPreference(key: String, value: Any) {
        when (value) {
            is String -> preferences.edit().putString(key, value.toString()).apply()
            is Int -> preferences.edit().putInt(key, value.toInt()).apply()
            is Float -> preferences.edit().putFloat(key, value.toFloat()).apply()
            is Long -> preferences.edit().putLong(key, value.toLong()).apply()
            is Boolean -> preferences.edit().putBoolean(key, value).apply()
            else -> return
        }
    }

    fun getString(key: String, defValue: String) = preferences.getString(key, defValue)
    fun getInt(key: String, defValue: Int) = preferences.getInt(key, defValue)
    fun getFloat(key: String, defValue: Float) = preferences.getFloat(key, defValue)
    fun getLong(key: String, defValue: Long) = preferences.getLong(key, defValue)
    fun getBoolean(key: String, defValue: Boolean) = preferences.getBoolean(key, defValue)
}