package com.example.wanderseasons.repository

import com.example.wanderseasons.model.LoginRequest
import com.example.wanderseasons.network.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun login(campus: String, req: LoginRequest) = api.login(campus, req)
    suspend fun getDashboard(keypass: String) = api.getDashboard(keypass)
}
