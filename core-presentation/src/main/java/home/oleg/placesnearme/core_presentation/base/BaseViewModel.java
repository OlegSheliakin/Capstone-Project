package home.oleg.placesnearme.core_presentation.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.smedialink.common.function.Action;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Oleg Sheliakin on 18.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class BaseViewModel<VIEW extends View> extends ViewModel {

    private final MutableLiveData<Action<VIEW>> observer = new MutableLiveData<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final Queue<Action<VIEW>> queue = new LinkedList<>();

    public MutableLiveData<Action<VIEW>> getObserver() {
        return observer;
    }

    protected boolean addToDisposables(Disposable disposable) {
        return compositeDisposable.add(disposable);
    }

    protected void setAction(Action<VIEW> action) {
        observer.setValue(action);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
