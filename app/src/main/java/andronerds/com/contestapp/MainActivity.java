package andronerds.com.contestapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;
import java.util.List;

import andronerds.com.contestapp.data.Achievements;
import andronerds.com.contestapp.data.Trip;
import andronerds.com.contestapp.data.Vehicle;
import andronerds.com.contestapp.fragments.HomeFragment;
import andronerds.com.contestapp.navDrawer.NavDrawerActivity;
import andronerds.com.contestapp.utils.IdentityStrings;
import butterknife.ButterKnife;
import butterknife.InjectView;




public class MainActivity extends NavDrawerActivity implements OnMapReadyCallback
{
    private CharSequence mTitle = "Home";
    private List<Achievements> achList;
    private Trip mTrip;

    @InjectView(R.id.main_activity_toolbar)Toolbar mToolbar;

    @Override
    protected Toolbar init()
    {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //init achievements
        this.initAchievements();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.home_fragment_container, homeFragment);
        fragmentTransaction.commit();

        //Toolbar settings
        //mToolbar.showOverflowMenu();
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);

        /*
            Testing sugar for achievements
         */


        Log.d("NAV_DRAWER", "Activating nav drawer activity");
        return mToolbar;
    }

    private void initAchievements()
    {
        //Achievements.deleteAll(Achievements.class);

        SharedPreferences userProfilePrefs = getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
        List<Achievements> achList = Achievements.find(Achievements.class, "name = ?", userProfilePrefs.getString(IdentityStrings.USER_NAME, "Name"));
        List<String> achTitles =  Arrays.asList(getResources().getStringArray(R.array.achievement_title));

        if(achList.size() == 0)
        {
            String[] achDesc = getResources().getStringArray(R.array.achievement_description);
            String[] earned = getResources().getStringArray(R.array.achievement_have_earned);
            Achievements ach;

            Log.d("ID CHECK MAIN","ID = " + R.drawable.ic_launcher);

            for(int i = 0; i < achTitles.size(); i++)
            {
                ach = new Achievements(achTitles.get(i),achDesc[i],earned[i].equals("true"),R.drawable.ic_launcher,userProfilePrefs.getString(IdentityStrings.USER_NAME, "Name"));
                ach.save();
            }
        }
        else if(achTitles.size() != achList.size())
        {
            Achievements.deleteAll(Achievements.class);
            String[] achDesc = getResources().getStringArray(R.array.achievement_description);
            String[] earned = getResources().getStringArray(R.array.achievement_have_earned);
            Achievements ach;

            Log.d("ID CHECK MAIN","ID = " + R.drawable.ic_profile_null);

            for(int i = 0; i < achTitles.size(); i++)
            {
                ach = new Achievements(achTitles.get(i),achDesc[i],earned[i].equals("true"),R.drawable.nav_vehicle_gray,userProfilePrefs.getString(IdentityStrings.USER_NAME, "Name"));
                ach.save();
            }
        }
    }

    public void openGMaps(Trip trip)
    {
        mTrip = trip;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MapFragment mapFragment = MapFragment.newInstance();
        mapFragment.getMapAsync(this);
        fragmentTransaction.replace(R.id.home_fragment_container, mapFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap map)
    {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        map.addMarker(new MarkerOptions()
                .position(new LatLng(mTrip.getmTripStartLat(), mTrip.getmTripStartLong()))
                .title("Start: " + mTrip.getmTripStart()));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(mTrip.getmTripEndLat(), mTrip.getmTripEndLong()))
                .title("End: " + mTrip.getmTripEnd()));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(mTrip.getmTripStartLat(), mTrip.getmTripStartLong()));
        builder.include(new LatLng(mTrip.getmTripEndLat(), mTrip.getmTripEndLong()));
        LatLngBounds bounds = builder.build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);

        map.animateCamera(cameraUpdate);
    }
}
