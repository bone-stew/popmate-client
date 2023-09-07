package com.example.popmate.util

import android.content.Context
import android.content.SharedPreferences
import com.example.popmate.model.data.local.PopupStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

//import com.google.gson.reflect.TypeToken

class PreferenceManager(context: Context) {
    companion object{

    private val PREFERNCE_NAME : String = "popmate"
    }
    private val prefs : SharedPreferences = context.getSharedPreferences(PREFERNCE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    fun <PopupStore> setList(key: String, list: List<PopupStore>){
        val gson = Gson()
        val json = gson.toJson(list)
        set(key, json)
    }

    fun set(key: String, value: String){
        editor.putString(key, value)
        editor.apply()
    }

    fun clear(){
        editor.clear()
        editor.apply()
    }

    fun getList(): List<PopupStore>? {
        val serializedObject = prefs.getString(PREFERNCE_NAME, null)
        return if (serializedObject != null) {
            val gson = Gson()
            val type: Type = object : TypeToken<List<PopupStore>>() {}.type
            gson.fromJson(serializedObject, type)
        } else {
            null
        }
    }
}
