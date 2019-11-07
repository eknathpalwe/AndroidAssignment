package com.androidassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidassignment.model.local.FactsLocalRepository
import com.androidassignment.model.remote.FactsRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: FactsRepository, private val localRepository: FactsLocalRepository,private val isOnline:Boolean) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FactsViewModel(repository, localRepository, isOnline) as T
    }
}