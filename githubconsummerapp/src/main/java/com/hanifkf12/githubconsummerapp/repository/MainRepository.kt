package com.hanifkf12.githubconsummerapp.repository

import android.content.Context
import com.hanifkf12.githubconsummerapp.model.User
import com.hanifkf12.githubconsummerapp.util.Const

class MainRepository(private val context: Context) {
    fun loadData(onData : (List<User>?) -> Unit)  {
        var size = 0
        val cursor = context.contentResolver.query(Const.CONTENT_URI,null,null,null,null)
        val users = mutableListOf<User>()
        cursor?.let {
            while (it.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow("avatarUrl"))
                val login = cursor.getString(cursor.getColumnIndexOrThrow("login"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                users.add(User(avatarUrl,id,login,name))
            }
            cursor.close()
        }
        onData(users)
    }
}