package home.oleg.placesnearme.presentation.feature.venue.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mahc.custombottomsheetbehavior.CustomBottomSheetBehavior;

import javax.inject.Inject;

import home.oleg.placesnearme.R;
import home.oleg.placesnearme.di.HasComponent;
import home.oleg.placesnearme.presentation.feature.map.di.PlacesMapFragmentComponent;
import home.oleg.placesnearme.presentation.feature.venue.viewmodel.VenueViewModel;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 20.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueDetailsView extends ConstraintLayout implements VenueView {

    private TextView tvVenueName;
    private TextView tvVenueAddress;
    private TextView tvVenueDescription;
    private TextView tvVenueDistance;
    private ImageView ivVenueIcon;

    private CustomBottomSheetBehavior behavior;

    public VenueDetailsView(Context context) {
        this(context, null);
    }

    public VenueDetailsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VenueDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        NestedScrollView nestedScrollView = getRootView().findViewById(R.id.nestedScrollView);
        behavior = CustomBottomSheetBehavior.from(nestedScrollView);
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

        getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    private void init() {
        LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_venue_details, this, true);

        tvVenueName = findViewById(R.id.tvVenueName);
        tvVenueAddress = findViewById(R.id.tvVenueAddress);
        tvVenueDescription = findViewById(R.id.tvVenueDescription);
        tvVenueDistance = findViewById(R.id.tvVenueDistance);
        ivVenueIcon = findViewById(R.id.ivVenueIcon);
    }

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            behavior.setState(CustomBottomSheetBehavior.STATE_COLLAPSED);
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    };
}
