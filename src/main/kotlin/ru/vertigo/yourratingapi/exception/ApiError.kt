package ru.vertigo.yourratingapi.exception

data class ApiError(
    val errorCode: String,
    val description: String,
)
