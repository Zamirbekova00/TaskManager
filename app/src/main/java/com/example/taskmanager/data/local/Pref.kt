package com.example.taskmanager.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Pref(context: Context) {
    private val pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)

    fun isUserSeen(): Boolean{
        return pref.getBoolean(ONBOARD_KEY, false)
    }

    fun saveSeen(){
        pref.edit().putBoolean(ONBOARD_KEY, true).apply()
    }
    fun setUser(name:String){
        pref.edit().putString(KEY_FOR_PROFILE, name).apply()

    }
    fun getUser():String {
        return pref.getString(KEY_FOR_PROFILE,"").toString()
    }

    fun setImage(image:String){
        pref.edit().putString(KEY_FOR_IMAGE, image).apply()
    }

    fun getImage():String {
        return pref.getString(KEY_FOR_IMAGE,"").toString()
    }

    companion object {
        const val PREF_NAME = "task.name.53"
        const val ONBOARD_KEY = "onBoardKey"
        const val KEY_FOR_PROFILE = "profile"
        const val KEY_FOR_IMAGE = "image"
    }
}