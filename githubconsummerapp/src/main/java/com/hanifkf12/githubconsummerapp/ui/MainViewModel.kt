package com.hanifkf12.githubconsummerapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hanifkf12.githubconsummerapp.model.User
import com.hanifkf12.githubconsummerapp.repository.MainRepository

class MainViewModel (application: Application) : AndroidViewModel(application){
    private val repository by lazy {
        MainRepository(application)
    }
    val users : MutableLiveData<List<User>?> = MutableLiveData()

    fun getUsers(){
        repository.loadData {
            users.value = it
        }
    }

}