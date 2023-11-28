package ru.evgendev.easypaytest.data.network.model

data class ResponseApi <T>(
    val success: String,
    val response: T,
    val error: ErrorDto?
)
