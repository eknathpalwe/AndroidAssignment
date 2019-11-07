package com.androidassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidassignment.data.OperationCallback
import com.androidassignment.model.Facts
import com.androidassignment.model.FactsDataSource
import com.androidassignment.model.FactsResponse

class FactsViewModel(private val repository: FactsDataSource) : ViewModel() {
    val _factsList = MutableLiveData<List<Facts>>().apply { value = emptyList() }
    val factsList: LiveData<List<Facts>> = _factsList

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: MutableLiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    init {
        loadFacts()
    }

    /**
     * load list of facts
     */
    fun loadFacts() {
        _isViewLoading.postValue(true)
        repository.getFactsList(object : OperationCallback<FactsResponse> {
            override fun onSuccess(obj: FactsResponse?) {
                _isViewLoading.postValue(false)
                obj?.rows?.let {
                    if (it.isEmpty()) {
                        _isEmptyList.postValue(true)
                    } else {
                        _factsList.postValue(
                            it.filter { facts -> (facts.title != null || facts.imageHref != null || facts.description != null) })
                        _title.postValue(obj.title)
                    }
                }

            }

            override fun onError(obj: Any?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(obj)
            }
        })
    }
}