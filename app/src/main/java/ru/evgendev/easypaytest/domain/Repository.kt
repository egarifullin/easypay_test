package ru.evgendev.easypaytest.domain

import com.google.gson.JsonObject
import retrofit2.Response
import ru.evgendev.easypaytest.data.network.model.PaymentDto
import ru.evgendev.easypaytest.data.network.model.ResponseApi
import ru.evgendev.easypaytest.data.network.model.TokenDto

interface Repository {
    suspend fun login(
        bodyRequest: JsonObject
    ): Response<ResponseApi<TokenDto>>?

    suspend fun getPayments(
        token: String
    ): Response<ResponseApi<List<PaymentDto>>>?
}