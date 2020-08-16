package com.hanifkf12.finalsubmissionfundamental.repository

import com.hanifkf12.finalsubmissionfundamental.db.UserGithubDao
import com.hanifkf12.finalsubmissionfundamental.model.response.DetailUserResponse

class DbRepository(private val dao: UserGithubDao){
    suspend fun insert(data: DetailUserResponse){
        dao.insert(data)
    }

    suspend fun getSingleUser(id : Int, onData : (DetailUserResponse?)->Unit){
        val user = dao.getSingleUser(id)
        onData(user)
    }

    suspend fun getFavoriteUsers(onData: (List<DetailUserResponse>?) -> Unit){
        val users = dao.getUsersList()
        onData(users)
    }

    suspend fun delete(data: DetailUserResponse){
        dao.delete(data)
    }
}