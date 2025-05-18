package com.example.wanderseasons.network

import com.example.wanderseasons.model.DashboardResponse
import com.example.wanderseasons.model.LoginRequest
import com.example.wanderseasons.model.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("{campus}/auth")
    suspend fun login(@Path("campus") campus: String, @Body request: LoginRequest): Response<LoginResponse>

    @GET("dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): Response<DashboardResponse>
}
