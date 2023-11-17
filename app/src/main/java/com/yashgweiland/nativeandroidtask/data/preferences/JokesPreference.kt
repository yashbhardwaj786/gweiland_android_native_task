package com.yashgweiland.nativeandroidtask.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.yashgweiland.nativeandroidtask.data.ResultData
import timber.log.Timber
import java.lang.reflect.Type


class JokesPreference(context: Context) {
    companion object {
        const val SHARED_PREF_KEY_PACKAGE = "com.arpit.unlimit"
        private const val PREF_KEY = "SAVED_JOKES"
    }

    private var moshi = Moshi.Builder()
        .build()
    private val type: Type =
        Types.newParameterizedType(MutableList::class.java, ResultData::class.java)
    private val jsonAdapter: JsonAdapter<ArrayList<ResultData>> = moshi.adapter(type)

    private val sharedPrefs = context.getSharedPreferences(
        SHARED_PREF_KEY_PACKAGE,
        Context.MODE_PRIVATE
    )

    fun saveJoke(data: ArrayList<ResultData>) {
        val json: String = jsonAdapter.toJson(data)
        sharedPrefs.set(PREF_KEY, json)
    }

    fun getSavedJokes(): ArrayList<ResultData> {
        val jsonText: String? = sharedPrefs.getString(PREF_KEY, "")
        return jsonText?.let {
            if (it.isNotEmpty())
                jsonAdapter.fromJson(it)
            else
                arrayListOf()
        } ?: run {
            arrayListOf()
        }
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value.toInt()) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value.toFloat()) }
            is Long -> edit { it.putLong(key, value.toLong()) }
            else -> {
                Timber.d("SSODebug: Preferences Unsupported Type: $value")
            }
        }
    }
}