package com.hanifkf12.finalsubmissionfundamental.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hanifkf12.finalsubmissionfundamental.R
import com.hanifkf12.finalsubmissionfundamental.db.MyDatabase
import com.hanifkf12.finalsubmissionfundamental.model.response.DetailUserResponse
import com.hanifkf12.finalsubmissionfundamental.model.response.FollowersResponseItem
import com.hanifkf12.finalsubmissionfundamental.model.response.FollowingResponseItem
import com.hanifkf12.finalsubmissionfundamental.repository.DbRepository
import com.hanifkf12.finalsubmissionfundamental.repository.NetworkRepository
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application){
    private val database : MyDatabase by lazy {
        MyDatabase.getDatabase(application)
    }
    private val repository: NetworkRepository by lazy {
        NetworkRepository()
    }
    private val dbRepository : DbRepository by lazy {
        DbRepository(database.userGithubDao())
    }
    private val context = application
    val data : MutableLiveData<DetailUserResponse> = MutableLiveData()
    val dataDb : MutableLiveData<DetailUserResponse?> = MutableLiveData()
    val status : MutableLiveData<String?> = MutableLiveData()
    val followers : MutableLiveData<List<FollowersResponseItem>?> = MutableLiveData()
    val following : MutableLiveData<List<FollowingResponseItem>?> = MutableLiveData()
    val loading : MutableLiveData<Boolean> = MutableLiveData()

    fun getDetailUser(username : String){
        repository.getDetailUser(username,{
            data.value = it
        },{

        },{

        })
    }

    fun getSingleUser(id : Int) = viewModelScope.launch{
        dbRepository.getSingleUser(id) {
            dataDb.value = it
        }
    }

    fun addFavorite(data: DetailUserResponse) = viewModelScope.launch{
        dbRepository.insert(data)
        status.value = context.getString(R.string.add_fav)
    }

    fun removeFavorite(data: DetailUserResponse) = viewModelScope.launch{
        dbRepository.delete(data)
        status.value = context.getString(R.string.remove_fav)

    }

    fun getUserFollowers(username: String){
        loading.value = true
        repository.getUserFollowers(username,{
            followers.value = it
            loading.value = false

        },{
            loading.value = false

        },{
            loading.value = false

        })
    }

    fun getUserFollowing(username: String){
        loading.value = true
        repository.getUserFollowing(username,{
            following.value = it
            loading.value = false

        },{
            loading.value = false

        },{
            loading.value = false

        })
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }
}