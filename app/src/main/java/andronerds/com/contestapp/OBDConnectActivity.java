package andronerds.com.contestapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import andronerds.com.contestapp.fragments.OBDConnectFragment;
import andronerds.com.contestapp.navDrawer.NavDrawerActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/23/15
 */
public class OBDConnectActivity extends NavDrawerActivity
{
    private CharSequence mTitle = "Connect an OBD";

    @InjectView(R.id.obd_connect_toolbar)Toolbar mToolbar;

    @Override
    protected Toolbar init()
    {
        setContentView(R.layout.activity_obd_connect);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OBDConnectFragment obdConnectFragment = new OBDConnectFragment();
        fragmentTransaction.add(R.id.obd_connect_fragment_container, obdConnectFragment);
        fragmentTransaction.commit();

        mToolbar.showOverflowMenu();
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);

        return mToolbar;
    }
}
