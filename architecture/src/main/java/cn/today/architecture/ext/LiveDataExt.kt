package cn.today.architecture.ext

import androidx.annotation.AnyThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@AnyThread
inline fun <reified T> MutableLiveData<T>.postNext(map: (T) -> T) {
    postValue(verifyLiveDataNotEmpty())
}

@AnyThread
inline fun <reified T> MutableLiveData<T>.setNext(map: (T) -> T) {
    value = map(verifyLiveDataNotEmpty())
}

@AnyThread
inline fun <reified T> LiveData<T>.verifyLiveDataNotEmpty(): T {
    return value
        ?: throw NullPointerException("MutableLiveData<${T::class.java}> not contain value.")
}