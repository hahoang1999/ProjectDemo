package com.example.projectdemo.network

import com.example.projectdemo.network.Config.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private var instance: Retrofit? = null

    private fun retrofitClient(): Retrofit {
        return instance ?: Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .apply {
                instance = this
            }
    }

    fun getService(): ApiInterface = retrofitClient().create(ApiInterface::class.java)
}