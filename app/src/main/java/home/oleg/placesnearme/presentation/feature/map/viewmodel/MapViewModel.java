package home.oleg.placesnearme.presentation.feature.map.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import home.oleg.placenearme.interactors.GetVenuesInteractor;
import home.oleg.placenearme.repositories.Category;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placenearme.utils.Supplier;
import home.oleg.placesnearme.presentation.viewobjects.VenueViewObject;
import home.oleg.placesnearme.utils.LazyDelegate;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;

/**
 * Created by Oleg on 10.08.2018.
 */
public class MapViewModel extends ViewModel {

    private final GetVenuesInteractor interactor;

    private Lazy<MutableLiveData<List<VenueViewObject>>> results = LazyDelegate.delegate(MutableLiveData::new);
    private Lazy<MutableLiveData<Throwable>> errors = LazyDelegate.delegate(MutableLiveData::new);
    private Disposable disposable;

    @Inject
    public MapViewModel(GetVenuesInteractor interactor) {
        this.interactor = interactor;
    }

    public MutableLiveData<List<VenueViewObject>> observeResults() {
        return results.get();
    }

    public MutableLiveData<Throwable> observeErrors() {
        return errors.get();
    }

    public void getRecommendedVenues() {
        disposable = interactor.getRecommendedVenues()
                .map(VenueViewObject::mapFrom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results.get()::setValue, errors.get()::setValue);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
