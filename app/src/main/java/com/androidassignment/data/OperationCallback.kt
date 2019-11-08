package com.androidassignment.data

/**
 * Generic operation callback to received data as success or failure
 * @param T the type of instance
 */
interface OperationCallback<T> {
    /**
     * @param T the type of instance which is from OperationCallback<T>
     */
    fun onSuccess(obj: T?)

    /**
     * @param obj error message information object of any
     */
    fun onError(obj: Any?)
}