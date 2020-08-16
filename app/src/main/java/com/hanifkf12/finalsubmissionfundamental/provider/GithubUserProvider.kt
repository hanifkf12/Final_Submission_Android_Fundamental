package com.hanifkf12.finalsubmissionfundamental.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.hanifkf12.finalsubmissionfundamental.db.MyDatabase
import com.hanifkf12.finalsubmissionfundamental.db.UserGithubDao

class GithubUserProvider : ContentProvider() {
    companion object{
        private const val AUTHORITY = "com.hanifkf12.finalsubmissionfundamental.provider"
        private const val TABLE_NAME = "users_table"
        private const val USERS = 1
        private const val USER = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
            sUriMatcher.addURI(AUTHORITY,
                "$TABLE_NAME/#",
                USERS)
        }
    }
    private lateinit var userGithubDao: UserGithubDao
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
       return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        context?.let {
            val database = MyDatabase.getDatabase(it)
            userGithubDao = database.userGithubDao()
        }
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            USER -> userGithubDao.getUsersCursor()
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
