package home.oleg.placesnearme.mapMVP.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.IFourSquareAPI;
import home.oleg.placesnearme.mapMVP.IMapInteractor;
import home.oleg.placesnearme.mapMVP.IMapPresenter;
import home.oleg.placesnearme.retrofit_models.FullResponse;
import home.oleg.placesnearme.retrofit_models.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oleg on 18.04.2016.
 */
public class MapInteractorImpl implements IMapInteractor {

    private final String PATH = "https://api.foursquare.com/";
    private IMapPresenter mapPresenter;

    public MapInteractorImpl(IMapPresenter mapPresenter) {
        this.mapPresenter = mapPresenter;
    }

    @Override
    public void sendRequest(Map<String, String> queryMap) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IFourSquareAPI adapter = retrofit.create(IFourSquareAPI.class);
        Call<FullResponse> call = adapter.getItems(queryMap);

        call.enqueue(new Callback<FullResponse>() {
            List<Item> items = new ArrayList<>();
            FullResponse fullResponse = null;

            @Override
            public void onResponse(Call<FullResponse> call, Response<FullResponse> response) {
                FullResponse fullResponse = response.body();
                items = fullResponse.getResponse()
                        .getGroups()
                        .get(0)
                        .getItems();
                if (mapPresenter.isViewAttached()) {
                    mapPresenter.onFinished(items);
                }
            }

            @Override
            public void onFailure(Call<FullResponse> call, Throwable t) {
                if (mapPresenter.isViewAttached()) {
                    mapPresenter.onFailed();
                }
            }
        });
    }
}


