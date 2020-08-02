package br.testesoftdesign.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.testesoftdesign.repository.Repository

class Factory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}