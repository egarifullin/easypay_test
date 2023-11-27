package ru.evgendev.easypaytest.data.network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.evgendev.easypaytest.data.network.model.ResponseApi

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body bodyRequest: JsonObject
    ): Response<ResponseApi>

    @GET("payments")
    suspend fun getPayments(
        @Header("token") token: String
    ): Response<ResponseApi>
}