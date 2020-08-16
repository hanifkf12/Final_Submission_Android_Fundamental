package com.hanifkf12.finalsubmissionfundamental.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hanifkf12.finalsubmissionfundamental.model.response.DetailUserResponse

@Database(entities = [DetailUserResponse::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase(){
    abstract fun userGithubDao() : UserGithubDao


    companion object{
        @Volatile
        private var INSTANCE : MyDatabase? = null
        fun getDatabase(context: Context) : MyDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}