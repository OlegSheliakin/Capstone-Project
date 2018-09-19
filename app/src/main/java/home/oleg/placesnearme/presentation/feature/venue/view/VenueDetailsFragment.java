package home.oleg.placesnearme.presentation.feature.venue.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import dagger.Lazy;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.di.HasComponent;
import home.oleg.placesnearme.presentation.feature.map.di.PlacesMapFragmentComponent;
import home.oleg.placesnearme.presentation.feature.venue.viewmodel.VenueViewModel;
import home.oleg.placesnearme.presentation.view_action.ViewActionObserver;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;

public class VenueDetailsFragment extends Fragment implements VenueView {

    @Inject
    VenueViewModel venueViewModel;

    private TextView tvVenueName;
    private TextView tvVenueAddress;
    private TextView tvVenueDescription;
    private TextView tvVenueDistance;
    private ImageView ivVenueIcon;

    public VenueDetailsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((HasComponent<PlacesMapFragmentComponent>) getParentFragment()).get()
                .venueDetailsFragmentComponentBuilder()
                .bind(this)
                .build()
                .inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venue_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvVenueName = view.findViewById(R.id.tvVenueName);
        tvVenueAddress = view.findViewById(R.id.tvVenueAddress);
        tvVenueDescription = view.findViewById(R.id.tvVenueDescription);
        tvVenueDistance = view.findViewById(R.id.tvVenueDistance);
        ivVenueIcon = view.findViewById(R.id.ivVenueIcon);

        venueViewModel.getObserver().observe(this, ViewActionObserver.create(this));
    }

    @Override
    public void showVenue(VenueViewData venueViewData) {
        tvVenueName.setText(venueViewData.getTitle());
        tvVenueAddress.setText(venueViewData.getAddress());
        tvVenueDescription.setText(venueViewData.getDescription());
        tvVenueDistance.setText("100m"); //TODO

        if (!venueViewData.getPhotoUrls().isEmpty()) {
            Glide.with(ivVenueIcon)
                    .load(venueViewData.getPhotoUrls().get(0))
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivVenueIcon);
        }
    }
}
