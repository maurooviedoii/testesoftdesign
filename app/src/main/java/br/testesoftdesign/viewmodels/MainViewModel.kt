package br.testesoftdesign.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.testesoftdesign.models.v2.Base
import br.testesoftdesign.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    private var resultSearchByName: MutableLiveData<Base> = MutableLiveData()

    fun searchByName(name: String) {
        repository.searchByName(name = name).enqueue(object : Callback<Base> {
            override fun onFailure(call: Call<Base>, t: Throwable) {
                resultSearchByName.value = Base(code = -1, total = -1, data = null)
            }

            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                resultSearchByName.value = response.body()
            }
        })
    }

    fun getResultSearchByName(): MutableLiveData<Base> {
        return resultSearchByName
    }

    fun clearMutables() {
        resultSearchByName.value = null
    }


}