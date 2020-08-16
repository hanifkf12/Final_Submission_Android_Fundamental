package com.hanifkf12.finalsubmissionfundamental.db

import android.database.Cursor
import androidx.room.*
import com.hanifkf12.finalsubmissionfundamental.model.response.DetailUserResponse

@Dao
interface UserGithubDao {
    @Query("SELECT * FROM users_table")
    suspend fun getUsersList() : List<DetailUserResponse>

    @Query("SELECT * FROM users_table")
    fun getUsersCursor() : Cursor

    @Query("SELECT * FROM users_table WHERE id = :id")
    suspend fun getSingleUser(id : Int) : DetailUserResponse?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data : DetailUserResponse)

    @Delete
    suspend fun delete(data: DetailUserResponse)
}