package andronerds.com.contestapp.cards;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
public class MyTripsTripCard extends Card implements OnMapReadyCallback, GoogleMap.SnapshotReadyCallback, GoogleMap.OnMapLoadedCallback
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

    public LinearLayout getClickLayout()
    {
        return mMoreDetailsLayout;
    }

    @Override
    public void onSnapshotReady(Bitmap bitmap)
    {
        Log.d("SNAPSHOT", "Snapshot is ready");
        try {
            //mTripMapView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapLoaded()
    {
        Log.d("MAP LOADED", "Your map has been loaded");
        mMap.snapshot(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.addMarker(new MarkerOptions()
                .position(mTrip.getmTripStartLatLng())
                .title("Start"));
        mMap.addMarker(new MarkerOptions()
                .position(mTrip.getmTripEndLatLng())
                .title("End"));

        MapsInitializer.initialize(this.getContext());

        double cameraLat = (mTrip.getmTripStartLatLng().latitude + mTrip.getmTripEndLatLng().latitude);
        cameraLat = cameraLat / 2;
        double cameraLong = (mTrip.getmTripStartLatLng().longitude + mTrip.getmTripEndLatLng().longitude);
        cameraLong = cameraLong / 2;

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(cameraLat, cameraLong), 10);

        mMap.moveCamera(cameraUpdate);

        mMap.setOnMapLoadedCallback(this);
    }
}
