package andronerds.com.contestapp.cards;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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


        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        String startAdd = "";
        String endAdd = "";
        try
        {
            List<Address> startAddrList = geocoder.getFromLocation(mTrip.getmTripStartLat(), mTrip.getmTripStartLong(), 1);
            Address start = startAddrList.get(0);
            startAdd = start.getAddressLine(1);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            List<Address> endAddrList = geocoder.getFromLocation(mTrip.getmTripEndLat(), mTrip.getmTripEndLong(), 1);
            Address end = endAddrList.get(0);
            endAdd = end.getAddressLine(1);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        startAdd = startAdd.replaceAll("\\d","");
        endAdd = endAdd.replaceAll("\\d","");

        mFromText.setText(startAdd);
        mToText.setText(endAdd);


        Picasso.with(this.getContext())
                .load(R.drawable.ph_map_marker)
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
