package com.androidassignment.model.remote

import android.util.Log
import com.androidassignment.App
import com.androidassignment.data.ApiClient
import com.androidassignment.data.OperationCallback
import com.androidassignment.model.FactsDataSource
import com.androidassignment.model.FactsResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FactsRepository : FactsDataSource {
    private var call: Call<FactsResponse>? = null

    override fun getFactsList(operationCallback: OperationCallback<FactsResponse>?) {
        call = ApiClient.build()?.getFactsList()
        call?.enqueue(object : Callback<FactsResponse> {
            override fun onFailure(call: Call<FactsResponse>, t: Throwable) {
                operationCallback?.onError(t.message)
            }

            override fun onResponse(call: Call<FactsResponse>, response: Response<FactsResponse>) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        App.prefs?.responseData = Gson().toJson(it).toString();
                        operationCallback?.onSuccess(it)
                    } else {
                        operationCallback?.onError(response.errorBody())
                    }
                }
            }
        })
    }
}