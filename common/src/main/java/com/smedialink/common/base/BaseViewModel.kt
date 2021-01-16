package com.smedialink.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.smedialink.common.ext.reduceState
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Oleg Sheliakin on 06.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

abstract class BaseViewModel<STATE : Any> : ViewModel() {

    protected val disposables: CompositeDisposable by lazy {
        return@lazy CompositeDisposable()
    }

    protected val stateInternal: MutableLiveData<STATE> by lazy {
        return@lazy MutableLiveData<STATE>()
    }

    val state: LiveData<STATE> = stateInternal

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    protected fun Disposable.autoDispose() : Disposable {
        disposables.add(this)
        return this
    }

    protected fun reduce(operation: (STATE) -> STATE) {
        stateInternal.reduceState {
            operation(it)
        }
    }
}
