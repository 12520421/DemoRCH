package com.atg.demorch.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {

    val onLoading: MutableLiveData<Boolean> = MutableLiveData()
    val onNetworkError: MutableLiveData<String> = MutableLiveData()

    protected val parentJob = Job()
    protected val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO

    protected val scope = CoroutineScope(coroutineContext)

    protected var job: Job? = null

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}