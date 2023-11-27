package ru.evgendev.easypaytest.domain

import com.google.gson.JsonObject
import retrofit2.Response
import ru.evgendev.easypaytest.data.network.model.ResponseApi

interface Repository {
    suspend fun login(
        bodyRequest: JsonObject
    ): Response<ResponseApi>?

    suspend fun getPayments(
        token: String
    ): Response<ResponseApi>?
}