package com.androidassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidassignment.model.FactsDataSource

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository:FactsDataSource): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FactsViewModel(repository) as T
    }
}