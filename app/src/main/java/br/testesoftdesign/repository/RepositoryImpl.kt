package br.testesoftdesign.repository

import br.testesoftdesign.models.v2.Base
import retrofit2.Call

class RepositoryImpl(private val api: Endpoint) : Repository {

    override fun searchByName(name: String): Call<Base> {
        return api.searchByName(name = name)
    }
}