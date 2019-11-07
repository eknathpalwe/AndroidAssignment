package com.androidassignment.data

interface OperationCallback<T> {
    fun onSuccess(obj:T?)
    fun onError(obj:Any?)
}