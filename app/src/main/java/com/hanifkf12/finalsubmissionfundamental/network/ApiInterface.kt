package com.hanifkf12.finalsubmissionfundamental.network


import com.hanifkf12.finalsubmissionfundamental.model.response.DetailUserResponse
import com.hanifkf12.finalsubmissionfundamental.model.response.FollowersResponse
import com.hanifkf12.finalsubmissionfundamental.model.response.FollowingResponse
import com.hanifkf12.finalsubmissionfundamental.model.response.GithubResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("search/users?per_page=50")
    fun getUser(@Query("q") username : String) : Observable<Response<GithubResponse>>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String) : Observable<Response<DetailUserResponse>>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String) : Observable<Response<FollowersResponse>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String) : Observable<Response<FollowingResponse>>

}