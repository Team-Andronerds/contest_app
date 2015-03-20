package andronerds.com.contestapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import andronerds.com.contestapp.fragments.trips.MyTripsFragment;
import andronerds.com.contestapp.navDrawer.NavDrawerActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v0.1A
 * @since 2/19/15
 */
public class MyTripsActivity extends NavDrawerActivity
{
    private CharSequence mTitle = "My Trips";

    @InjectView(R.id.my_trips_toolbar)Toolbar mToolbar;

    @Override
    protected Toolbar init()
    {
        setContentView(R.layout.activity_my_trips);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MyTripsFragment myTripsFragment = new MyTripsFragment();
        fragmentTransaction.add(R.id.my_trips_fragment_container, myTripsFragment);
        fragmentTransaction.commit();

        mToolbar.showOverflowMenu();
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);

        return mToolbar;
    }
}
