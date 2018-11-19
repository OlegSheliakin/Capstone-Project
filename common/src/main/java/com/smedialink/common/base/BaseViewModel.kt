package com.smedialink.common.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Oleg Sheliakin on 06.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

abstract class BaseViewModel : ViewModel() {

    protected val disposables: CompositeDisposable by lazy {
        return@lazy CompositeDisposable()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}

