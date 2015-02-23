package andronerds.com.contestapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import andronerds.com.contestapp.fragments.MyVehicleFragment;
import andronerds.com.contestapp.navDrawer.NavDrawerActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0A
 * @since 2/19/15
 */
public class MyVehicleActivity extends NavDrawerActivity
{
    private CharSequence mTitle = "My Vehicle";

    @InjectView(R.id.my_vehicle_toolbar)Toolbar mToolbar;

    @Override
    protected Toolbar init()
    {
        setContentView(R.layout.activity_my_vehicle);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MyVehicleFragment myVehicleFragment = new MyVehicleFragment();
        fragmentTransaction.add(R.id.my_vehicle_fragment_container, myVehicleFragment);
        fragmentTransaction.commit();

        mToolbar.showOverflowMenu();
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);

        return mToolbar;
    }
}
