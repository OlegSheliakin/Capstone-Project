package home.oleg.placesnearme.presentation.feature.map.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import home.oleg.placenearme.repositories.Category;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placesnearme.presentation.viewobjects.VenueViewObject;
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

    private final VenueRepository venueRepository;
    private AsyncSubject<List<VenueViewObject>> searchSubject = AsyncSubject.create();
    private Disposable disposable;

    @Inject
    public MapViewModel(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Observable<List<VenueViewObject>> observeResults() {
        return searchSubject;
    }

    public void search(String query) {
        disposable = venueRepository.search(query)
                .map(VenueViewObject::mapFrom)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        searchSubject::onNext,
                        searchSubject::onError);
    }

    public void getRecommended(String type) {
        disposable = venueRepository.getRecommendedByCategory(Category.COFFEE)
                .map(VenueViewObject::mapFrom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        searchSubject::onNext,
                        searchSubject::onError);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(disposable != null) {
            disposable.dispose();
        }
    }
}
