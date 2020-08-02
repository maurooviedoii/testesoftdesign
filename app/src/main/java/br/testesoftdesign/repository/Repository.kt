package br.testesoftdesign.repository

import br.testesoftdesign.models.v2.Base
import retrofit2.Call

interface Repository {
    fun searchByName(name: String): Call<Base>
}