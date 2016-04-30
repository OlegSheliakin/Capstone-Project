package home.oleg.placesnearme.map_mvp.impl;

import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.IFourSquareAPI;
import home.oleg.placesnearme.map_mvp.IMapInteractor;
import home.oleg.placesnearme.map_mvp.IMapPresenter;
import home.oleg.placesnearme.models.FullResponse;
import home.oleg.placesnearme.models.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by Oleg on 18.04.2016.
 */
public class MapInteractorImpl implements IMapInteractor {

    private static final String PATH = "https://api.foursquare.com/";
    private IMapPresenter mapPresenter;
    private IFourSquareAPI adapter;

    public MapInteractorImpl(IMapPresenter mapPresenter) {
        this.mapPresenter = mapPresenter;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.adapter = retrofit.create(IFourSquareAPI.class);
    }

    @Override
    public void sendRequest(Map<String, String> queryMap) {

        Call<FullResponse> call = adapter.getItems(queryMap);

        call.enqueue(new Callback<FullResponse>() {

            @Override
            public void onResponse(Call<FullResponse> call, Response<FullResponse> response) {
                if (response.isSuccessful()) {
                    FullResponse fullResponse = response.body();
                    List<Item> items = fullResponse.getResponse()
                            .getGroups()
                            .get(0)// recommended group
                            .getItems();
                    if (mapPresenter.isViewAttached()) {//checks the view is still attached
                        mapPresenter.onFinished(items);
                    }
                }else {
                    if (mapPresenter.isViewAttached()) {
                        mapPresenter.onFailed();
                    }
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



