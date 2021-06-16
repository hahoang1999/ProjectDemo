package com.example.projectdemo.network

import com.example.projectdemo.models.AccountModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("api/users/{username}")
    fun getUsersByUserName(
        @Path("username") username: String
    ): Call<AccountModel>

    @POST("api/users")
    fun createUser(
        @Body accountModel: AccountModel
    ): Call<AccountModel>

    @PUT("api/users/{username}")
    fun updateUser(
        @Path("username") username: String,
        @Body accountModel: AccountModel
    ): Call<AccountModel>

    @DELETE("api/users/{username}")
    fun deleteUser(
        @Path("username") username: String
    ): Call<AccountModel>

    @GET("api/users/")
    fun searchUserByParameter(
        @Query("status") status: String,
        @Query("phone") phone: String,
        @Query("email") email: String,
        @Query("username") username: String,
        @Query("firstName") firstname: String,
        @Query("lastName") lastname: String
    ): Call<ArrayList<AccountModel>>
}