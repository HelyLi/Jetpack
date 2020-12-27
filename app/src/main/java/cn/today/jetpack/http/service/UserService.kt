package cn.today.jetpack.http.service

import cn.today.jetpack.entity.ReceivedEvent
import cn.today.jetpack.entity.Repo
import cn.today.jetpack.entity.SearchResult
import cn.today.jetpack.entity.UserInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("user")
    suspend fun fetchUserOwner(): Response<UserInfo>

    @GET("users/{username}/received_events?")
    suspend fun queryReceivedEvents(
        @Path("username") username: String,
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int
    ): List<ReceivedEvent>

    @GET("users/{username}/repos?")
    suspend fun queryRepos(
        @Path("username") username: String,
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int,
        @Query("sort") sort: String
    ): List<Repo>

    @GET("search/repositories")
    suspend fun search(
        @Query("q") q: String,
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int
    ): SearchResult
}