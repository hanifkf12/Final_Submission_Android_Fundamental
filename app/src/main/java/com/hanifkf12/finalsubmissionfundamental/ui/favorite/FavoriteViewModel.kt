package com.hanifkf12.finalsubmissionfundamental.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hanifkf12.finalsubmissionfundamental.db.MyDatabase
import com.hanifkf12.finalsubmissionfundamental.model.response.DetailUserResponse
import com.hanifkf12.finalsubmissionfundamental.repository.DbRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val _users : MutableLiveData<List<DetailUserResponse>?> = MutableLiveData()
    val loading : MutableLiveData<Boolean> = MutableLiveData()
    val users : LiveData<List<DetailUserResponse>?> = _users

    private val database by lazy {
        MyDatabase.getDatabase(application)
    }
    private val repository by lazy {
        DbRepository(database.userGithubDao())
    }

    fun getFavoritesUser() = viewModelScope.launch{
        loading.apply {
            value = true
        }
        repository.getFavoriteUsers {
            _users.apply {
                value = it
                loading.apply {
                    value = false
                }
            }
        }
    }


}