package home.oleg.placesnearme.presentation.feature.venue.view;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import home.oleg.placesnearme.R;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;
import home.oleg.placesnearme.util.ImageLoader;

/**
 * Created by Oleg Sheliakin on 21.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueDetailsViewFacade implements VenueView {

    private VenueDetailsView venueDetailsView;
    private ImageView ivVenuePhoto;

    @Inject
    public VenueDetailsViewFacade() {

    }

    //todo add lifecycle observer
    public void onCreate(View view) {
        venueDetailsView = view.findViewById(R.id.venueView);
        ivVenuePhoto = view.findViewById(R.id.ivVenuePhoto);
    }

    @Override
    public void showVenue(VenueViewData venueViewData) {
        ImageLoader.loadIcon(ivVenuePhoto, venueViewData.getIconUrlGray());

        venueDetailsView.showVenue(venueViewData);
    }
}
