package andronerds.com.contestapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import andronerds.com.contestapp.data.Trip;
import andronerds.com.contestapp.fragments.trips.MyTripsFragment;
import andronerds.com.contestapp.navDrawer.NavDrawerActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v0.1A
 * @since 2/19/15
 */
public class MyTripsActivity extends NavDrawerActivity implements OnMapReadyCallback
{
    private CharSequence mTitle = "My Trips";

    private Trip mTrip;

    private static final String URI_SCHEME = "http";
    private static final String URI_SITE = "maps.google.com";

    @InjectView(R.id.my_trips_toolbar)Toolbar mToolbar;

    @Override
    protected Toolbar init()
    {
        setContentView(R.layout.activity_my_trips);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MyTripsFragment myTripsFragment = new MyTripsFragment();
        fragmentTransaction.replace(R.id.my_trips_fragment_container, myTripsFragment);
        fragmentTransaction.commit();

        mToolbar.showOverflowMenu();
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);

        return mToolbar;
    }

    public void testGMaps()
    {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MapFragment mapFragment = MapFragment.newInstance();
        mapFragment.getMapAsync(this);
        fragmentTransaction.replace(R.id.my_trips_fragment_container, mapFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void openGMaps(Trip trip)
    {
        mTrip = trip;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MapFragment mapFragment = MapFragment.newInstance();
        mapFragment.getMapAsync(this);
        fragmentTransaction.replace(R.id.my_trips_fragment_container, mapFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap map)
    {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        map.addMarker(new MarkerOptions()
                .position(new LatLng(mTrip.getmTripStartLat(), mTrip.getmTripStartLong()))
                .title(mTrip.getmTripStart()));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(mTrip.getmTripEndLat(), mTrip.getmTripEndLong()))
                .title(mTrip.getmTripEnd()));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(mTrip.getmTripStartLat(), mTrip.getmTripStartLong()));
        builder.include(new LatLng(mTrip.getmTripEndLat(), mTrip.getmTripEndLong()));
        LatLngBounds bounds = builder.build();

        double middleLat = mTrip.getmTripStartLat() + mTrip.getmTripEndLat();
        middleLat = middleLat / 2;
        double middleLong = mTrip.getmTripStartLong() + mTrip.getmTripEndLong();
        middleLong = middleLong / 2;

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);

        map.animateCamera(cameraUpdate);
    }
}
