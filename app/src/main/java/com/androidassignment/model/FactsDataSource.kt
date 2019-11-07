package com.androidassignment.model

import com.androidassignment.data.OperationCallback

interface FactsDataSource {
    fun getFactsList(operationCallback: OperationCallback<FactsResponse>?)

}