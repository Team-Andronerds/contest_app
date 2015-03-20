package andronerds.com.contestapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import andronerds.com.contestapp.fragments.PairedListLoadingFragment;
import andronerds.com.contestapp.fragments.SettingsConnectFragment;
import andronerds.com.contestapp.fragments.SettingsFragment;
import andronerds.com.contestapp.navDrawer.NavDrawerActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/23/15
 */
public class SettingsActivity extends NavDrawerActivity
{
    private CharSequence mTitle = "Settings";


    @InjectView(R.id.settings_toolbar)Toolbar mToolbar;

    @Override
    protected Toolbar init()
    {
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentTransaction.add(R.id.settings_fragment_container, settingsFragment);
        fragmentTransaction.commit();

        mToolbar.showOverflowMenu();
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);

        return mToolbar;
    }

    @Override
    public void onBackPressed() {
        PairedListLoadingFragment frag = (PairedListLoadingFragment) getFragmentManager().findFragmentByTag("loading");
        SettingsConnectFragment connectFrag = (SettingsConnectFragment) getFragmentManager().findFragmentByTag("devices");
        if (!frag.isVisible())
        {
            Log.d("BACK CALL", "loading not  visible");
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
                Log.d("BACK CALL", "super on back");
            }
            else if(connectFrag.isVisible()){
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.remove(connectFrag);
                ft.commit();
                getFragmentManager().popBackStack();

                Log.d("BACK CALL", "connect frag is visible");
            }
            else {
                getFragmentManager().popBackStack();
                Log.d("BACK CALL", "else");
            }
        }
    }
}
