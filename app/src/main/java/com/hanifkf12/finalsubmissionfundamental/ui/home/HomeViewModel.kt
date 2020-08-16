package com.hanifkf12.finalsubmissionfundamental.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hanifkf12.finalsubmissionfundamental.model.response.Item
import com.hanifkf12.finalsubmissionfundamental.repository.NetworkRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NetworkRepository by lazy {
        NetworkRepository()
    }
    private val _users : MutableLiveData<List<Item>> = MutableLiveData()
    val users: LiveData<List<Item>> = _users
    val loading : MutableLiveData<Boolean> = MutableLiveData()

    fun getUsersGithub(username : String){
        showLoading()
        repository.getUsersGithub(username,{
            _users.apply {
                it?.let {
                    value = it.items
                }
            }
        },{
            hideLoading()
        },{
            hideLoading()
        })
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }

    private fun showLoading(){
        loading.value = true

    }
    private fun hideLoading(){
        loading.value = false

    }

}