package home.oleg.placesnearme.core_presentation.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.smedialink.common.function.Action;

import java.util.LinkedList;
import java.util.Queue;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Oleg Sheliakin on 18.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public abstract class BaseViewModel<VIEW extends View> extends ViewModel {

    private MutableLiveData<Action<VIEW>> observer;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final Queue<Action<VIEW>> queue = new LinkedList<>();

    public MutableLiveData<Action<VIEW>> getObserver() {
        if(observer == null) {
            observer = new MutableLiveData<>();
            onObserverCreated();
        }
        return observer;
    }

    protected void onObserverCreated() {

    }

    protected boolean addToDisposables(Disposable disposable) {
        return compositeDisposable.add(disposable);
    }

    protected void setState(Action<VIEW> action) {
        observer.setValue(action);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
