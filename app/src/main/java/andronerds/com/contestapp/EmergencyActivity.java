package andronerds.com.contestapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import andronerds.com.contestapp.fragments.EmergencyFragment;
import andronerds.com.contestapp.navDrawer.NavDrawerActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v0.1A
 * @since 2/19/15
 */
public class EmergencyActivity extends NavDrawerActivity
{
    private CharSequence mTitle = "Emergency";

    @InjectView(R.id.emergency_toolbar)Toolbar mToolbar;

    @Override
    protected Toolbar init()
    {
        setContentView(R.layout.activity_emergency);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EmergencyFragment emergencyFragment = new EmergencyFragment();
        fragmentTransaction.add(R.id.emergency_fragment_container, emergencyFragment);
        fragmentTransaction.commit();

        //mToolbar.showOverflowMenu();
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);

        return mToolbar;
    }
}
