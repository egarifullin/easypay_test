package ru.evgendev.easypaytest.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://easypay.world/api-test/"

    private var retrofit: Retrofit? = null

    fun getService(): ApiService? {
        if (retrofit == null) {
            val client = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val request: Request =
                        chain.request().newBuilder()
                            .header("app-key", "12345")
                            .header("v", "1")
                            .build()
                    chain.proceed(request)
                })
                .followRedirects(false)
                .followSslRedirects(false)
                .build()

            retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit?.create(ApiService::class.java)
    }
}