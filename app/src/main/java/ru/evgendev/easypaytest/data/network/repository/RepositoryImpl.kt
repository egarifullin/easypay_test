package ru.evgendev.easypaytest.data.network.repository

import com.google.gson.JsonObject
import retrofit2.Response
import ru.evgendev.easypaytest.data.network.ApiFactory
import ru.evgendev.easypaytest.data.network.model.PaymentDto
import ru.evgendev.easypaytest.data.network.model.ResponseApi
import ru.evgendev.easypaytest.data.network.model.TokenDto
import ru.evgendev.easypaytest.domain.Repository

class RepositoryImpl : Repository {
    override suspend fun login(bodyRequest: JsonObject): Response<ResponseApi<TokenDto>>? {
        return ApiFactory.getService()?.login(bodyRequest)
    }

    override suspend fun getPayments(token: String): Response<ResponseApi<List<PaymentDto>>>? {
        return ApiFactory.getService()?.getPayments(token)
    }

}