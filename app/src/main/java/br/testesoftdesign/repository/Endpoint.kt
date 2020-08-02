package br.testesoftdesign.repository

import br.testesoftdesign.models.v2.Base
import br.testesoftdesign.utils.Enviroment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoint {

    @GET("/v1/public/characters")
    fun searchByName(
        @Query("apikey") apiKey: String = Enviroment.publicKey,
        @Query("hash") hash: String = Enviroment.hash(),
        @Query("limit") limit: Int = 1,
        @Query("ts") ts: Int = Enviroment.ts,
        @Query("nameStartsWith") name: String
    ): Call<Base>
}