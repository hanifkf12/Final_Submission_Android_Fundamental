package com.hanifkf12.githubconsummerapp.util

import android.net.Uri

object Const {
    private const val AUTHORITY = "com.hanifkf12.finalsubmissionfundamental.provider"
    private const val TABLE_NAME = "users_table"
    val CONTENT_URI: Uri = Uri.Builder().scheme("content")
        .authority(AUTHORITY)
        .appendPath(TABLE_NAME)
        .build()
}