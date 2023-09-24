package com.example.weatherapp.ext

import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS

fun Context.openAppSettings() {

    val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
        addCategory(CATEGORY_DEFAULT)
        addFlags(FLAG_ACTIVITY_NEW_TASK)
        addFlags(FLAG_ACTIVITY_NO_HISTORY)
        addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    }

    startActivity(intent)
}