package com.begoit.mooc.offline.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *@Description:用户信息持久化
 *@Author:gxj
 *@Time 2019/7/4
 */
open class SharedPreferencesUtils(context: Context) {
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    //用户信息
    var user by SharedPreferenceDelegates.string(defaultValue = "")
    var token by SharedPreferenceDelegates.string(defaultValue = "")

    private object SharedPreferenceDelegates {

        fun string(defaultValue: String? = null) = object : ReadWriteProperty<SharedPreferencesUtils, String?> {
            override fun getValue(thisRef: SharedPreferencesUtils, property: KProperty<*>): String? {
                return thisRef.preferences.getString(property.name, defaultValue)
            }

            override fun setValue(thisRef: SharedPreferencesUtils, property: KProperty<*>, value: String?) {
                thisRef.preferences.edit().putString(property.name, value).apply()
            }
        }
    }
}