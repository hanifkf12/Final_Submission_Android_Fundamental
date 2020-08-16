package com.hanifkf12.finalsubmissionfundamental.repository

import com.hanifkf12.finalsubmissionfundamental.model.response.DetailUserResponse
import com.hanifkf12.finalsubmissionfundamental.model.response.FollowersResponse
import com.hanifkf12.finalsubmissionfundamental.model.response.FollowingResponse
import com.hanifkf12.finalsubmissionfundamental.model.response.GithubResponse
import com.hanifkf12.finalsubmissionfundamental.network.ApiObserver
import com.hanifkf12.finalsubmissionfundamental.network.ApiResource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class NetworkRepository {
    private val compositeDisposable = CompositeDisposable()

    fun getUsersGithub(username : String, onResult : (GithubResponse?)->Unit, onError : (Throwable)->Unit, onLimit : (Boolean)->Unit){
        ApiResource.create().getUser(username).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : ApiObserver<Response<GithubResponse>>(compositeDisposable){
                override fun onApiSuccess(data: Response<GithubResponse>) {
                    if(data.code() == 200){
                        onResult(data.body())
                        onLimit(false)
                    }else if (data.code() == 403){
                        onResult(data.body())
                        onLimit(true)
                    }
                }
                override fun onApiError(er: Throwable) {
                    onError(er)
                }


            })
    }

    fun getDetailUser(username : String, onResult : (DetailUserResponse?)->Unit, onError : (Throwable)->Unit, onLimit : (Boolean)->Unit){
        ApiResource.create().getDetailUser(username).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : ApiObserver<Response<DetailUserResponse>>(compositeDisposable){
                override fun onApiSuccess(data: Response<DetailUserResponse>) {
                    if(data.code() == 200){
                        onResult(data.body())
                        onLimit(false)
                    }else if (data.code() == 403){
                        onResult(data.body())
                        onLimit(true)
                    }
                }
                override fun onApiError(er: Throwable) {
                    onError(er)
                }


            })
    }

    fun getUserFollowers(username : String, onResult : (FollowersResponse?)->Unit, onError : (Throwable)->Unit, onLimit : (Boolean)->Unit){
        ApiResource.create().getFollowers(username).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : ApiObserver<Response<FollowersResponse>>(compositeDisposable){
                override fun onApiSuccess(data: Response<FollowersResponse>) {
                    if(data.code() == 200){
                        onResult(data.body())
                        onLimit(false)
                    }else if (data.code() == 403){
                        onResult(data.body())
                        onLimit(true)
                    }
                }
                override fun onApiError(er: Throwable) {
                    onError(er)
                }


            })
    }

    fun getUserFollowing(username : String, onResult : (FollowingResponse?)->Unit, onError : (Throwable)->Unit, onLimit : (Boolean)->Unit){
        ApiResource.create().getFollowing(username).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : ApiObserver<Response<FollowingResponse>>(compositeDisposable){
                override fun onApiSuccess(data: Response<FollowingResponse>) {
                    if(data.code() == 200){
                        onResult(data.body())
                        onLimit(false)
                    }else if (data.code() == 403){
                        onResult(data.body())
                        onLimit(true)
                    }
                }
                override fun onApiError(er: Throwable) {
                    onError(er)
                }


            })
    }
    fun onDestroy(){
        compositeDisposable.clear()
    }
}