package br.testesoftdesign.utils

import br.testesoftdesign.repository.Endpoint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtils {
    private const val BASE_URL: String = "https://gateway.marvel.com"

    fun createEndpoint(): Endpoint {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(Endpoint::class.java)
    }
}