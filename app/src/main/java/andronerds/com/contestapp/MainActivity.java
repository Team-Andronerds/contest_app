package andronerds.com.contestapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import andronerds.com.contestapp.fragments.HomeFragment;
import andronerds.com.contestapp.navDrawer.NavDrawerActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends NavDrawerActivity
{
    private CharSequence mTitle = "Home";

    @InjectView(R.id.main_activity_toolbar)Toolbar mToolbar;

    @Override
    protected Toolbar init()
    {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.home_fragment_container, homeFragment);
        fragmentTransaction.commit();

        //Toolbar settings
        mToolbar.showOverflowMenu();
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);

        Log.d("NAV_DRAWER", "Activating nav drawer activity");
        return mToolbar;
    }
}
