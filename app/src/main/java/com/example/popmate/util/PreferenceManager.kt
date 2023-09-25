package com.example.popmate.util

import android.content.Context
import android.content.SharedPreferences
import com.example.popmate.model.data.local.PopupStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class PreferenceManager(context: Context) {
    companion object {
        private const val PREFERNCE_NAME: String = "popmate"
        private const val STORE_KEY: String = "storeKey"
        private const val STORE_ID_KEY: String = "storeIdKey"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFERNCE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    fun <PopupStore> setStoreList(key: String, list: List<PopupStore>) {
        val gson = Gson()
        val json = gson.toJson(list)
        set(key, json)
    }

    fun <Long> setStoreIdList(key: String, list: List<Long>) {
        val gson = Gson()
        val json = gson.toJson(list)
        set(key, json)
    }

    fun set(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun clear() {
        editor.clear()
        editor.apply()
    }

    fun getStoreList(): List<PopupStore>? {
        val serializedObject = prefs.getString(STORE_KEY, null)
        return if (serializedObject != null) {
            val gson = Gson()
            val type: Type = object : TypeToken<List<PopupStore>>() {}.type
            gson.fromJson(serializedObject, type)
        } else {
            null
        }
    }

    fun getStoreIdList(): List<Long>? {
        val serializedObject = prefs.getString(STORE_ID_KEY, null)
        return if (serializedObject != null) {
            val gson = Gson()
            val type: Type = object : TypeToken<List<Long>>() {}.type
            gson.fromJson(serializedObject, type)
        } else {
            null
        }
    }
}
