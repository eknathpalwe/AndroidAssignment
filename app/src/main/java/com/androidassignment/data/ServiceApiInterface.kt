package com.androidassignment.data

import com.androidassignment.model.FactsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ServicesApiInterface {
    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun getFactsList(): Call<FactsResponse>
}