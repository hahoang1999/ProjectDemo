package com.example.projectdemo.models

data class AccountModel(
    val email: String,
    val firstname: String,
    val lastname: String,
    val password: String,
    val phone: String,
    val status: String,
    val username: String,
    val userId: Int? = null
)