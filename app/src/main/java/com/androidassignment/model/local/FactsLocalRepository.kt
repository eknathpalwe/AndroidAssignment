package com.androidassignment.model.local

import com.androidassignment.App
import com.androidassignment.data.OperationCallback
import com.androidassignment.model.Facts
import com.androidassignment.model.FactsDataSource
import com.androidassignment.model.FactsResponse
import com.google.gson.Gson

class FactsLocalRepository : FactsDataSource {
    /**
     * Get list of facts from local cache
     */
    override fun getFactsList(operationCallback: OperationCallback<FactsResponse>?) {
        val responseData = App.prefs?.responseData
        responseData?.let {
            operationCallback?.onSuccess(Gson().fromJson(responseData, FactsResponse::class.java))
        } ?: FactsResponse("Default", listOf())

    }
}