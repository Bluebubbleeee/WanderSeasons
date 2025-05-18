package com.example.wanderseasons.model

data class DashboardResponse(
    val entities: List<Map<String, Any>>,
    val entityTotal: Int
)

