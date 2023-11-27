package ru.evgendev.easypaytest.data.network.model

data class ResponseApi(
    val success: String,
    val response: Any?,
    val error: ErrorDto?
)
