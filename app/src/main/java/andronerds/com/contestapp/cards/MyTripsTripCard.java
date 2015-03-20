package andronerds.com.contestapp.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.squareup.picasso.Picasso;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.Trip;
import butterknife.ButterKnife;
import butterknife.InjectView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v0.1A
 * @since 2/20/15
 */
public class MyTripsTripCard extends Card
{
    private Trip mTrip;
    private GoogleMap mMap;

    @InjectView(R.id.card_trip_image)ImageView mCardImage;
    @InjectView(R.id.card_trip_from)TextView mFromText;
    @InjectView(R.id.card_trip_to)TextView mToText;
    @InjectView(R.id.more_details_image)ImageView mMoreDetailsImage;
    @InjectView(R.id.click_details_view)LinearLayout mMoreDetailsLayout;

    public MyTripsTripCard(Context context, Trip trip)
    {
        super(context, R.layout.card_my_trips_trip);
        this.mTrip = trip;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view)
    {
        super.setupInnerViewElements(parent, view);
        ButterKnife.inject(this, view);
        GoogleMapOptions options = new GoogleMapOptions().liteMode(true);

        //mTripMapView.onCreate(null);
        //mTripMapView.getMapAsync(this);

        //mTripMapView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.map_placeholder));

        mFromText.setText(mTrip.getmTripStart());
        mToText.setText(mTrip.getmTripEnd());


        Picasso.with(this.getContext())
                .load(R.drawable.map_placeholder)
                .fit()
                .into(mCardImage);


        Picasso.with(this.getContext())
                .load(R.drawable.abc_ic_ab_back_mtrl_am_alpha)
                .rotate(180)
                .centerInside()
                .fit()
                .into(mMoreDetailsImage);
    }

    public Trip getTrip()
    {
        return this.mTrip;
    }
}
