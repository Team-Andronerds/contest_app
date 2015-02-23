package andronerds.com.contestapp.navDrawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;

import andronerds.com.contestapp.DriveToWinActivity;
import andronerds.com.contestapp.EmergencyActivity;
import andronerds.com.contestapp.InsuranceInfoActivity;
import andronerds.com.contestapp.MainActivity;
import andronerds.com.contestapp.MyTripsActivity;
import andronerds.com.contestapp.MyVehicleActivity;
import andronerds.com.contestapp.R;
import butterknife.InjectView;

/**
 * @author Chris Portokalis
 * @version ContestApp v0.1A
 * @since 2/19/15
 */
public abstract class NavDrawerActivity extends ActionBarActivity
{
    private final String ACTION_HOME = "Home";
    private final String ACTION_DRIVE_TO_WIN = "Drive To Win";
    private final String ACTION_MY_TRIPS = "My Trips";
    private final String ACTION_MY_VEHICLE = "My Vehicle";
    private final String ACTION_INSURANCE_INFO = "Insurance Info";
    private final String ACTION_EMERGENCY = "Emergency";

    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private CharSequence mTitle;
    private ArrayList<NavDrawerItem> mNavDrawerItems;
    private Context mContext = this;

    abstract protected Toolbar init();

    @InjectView(R.id.nav_drawer_layout)DrawerLayout mDrawerLayout;
    @InjectView(R.id.left_drawer)ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Init sets content view and
        other specifics based on activity implementation */
        mToolbar = init();

        mTitle = mToolbar.getTitle();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavDrawerItems = getNavDrawerItems();
        mDrawerList.setAdapter(new NavDrawerAdapter(mNavDrawerItems, this));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_closed
        ) {


            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintColor(this.getResources().getColor(R.color.status_bar_color));
        tintManager.setStatusBarTintEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nav_drawer, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_settings:
                Log.d("MENU", "Action settings clicked");
                break;
            case R.id.obd_connect:
                Log.d("MENU", "OBD connect clicked");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Log.i("sel", "onClick");
            //drawerItemLayout.setBackgroundColor(getResources().getColor(R.color.background_material_light));
            mDrawerLayout.closeDrawer(mDrawerList);

            Intent intent = null;
            Log.d("NAV DRAWER ITEM POS", mNavDrawerItems.get(position).getMenuItemName());

            switch(mNavDrawerItems.get(position).getMenuItemName()) {
                case ACTION_HOME:
                    Log.d("ACTIVITY_HOME", "Home activity initiated.");
                    intent = new Intent(mContext, MainActivity.class);
                    break;
                case ACTION_DRIVE_TO_WIN:
                    Log.d("ACTIVITY_DRIVE", "Drive to win activity initiated.");
                    intent = new Intent(mContext, DriveToWinActivity.class);
                    break;
                case ACTION_MY_TRIPS:
                    Log.d("ACTIVITY_TRIPS", "My trips activity initiated.");
                    intent = new Intent(mContext, MyTripsActivity.class);
                    break;
                case ACTION_MY_VEHICLE:
                    Log.d("ACTIVITY_VEHICLE", "My vehicle activity initiated.");
                    intent = new Intent(mContext, MyVehicleActivity.class);
                    break;
                case ACTION_INSURANCE_INFO:
                    Log.d("ACTIVITY_INSURANCE", "Insurance info activity initiated.");
                    intent = new Intent(mContext, InsuranceInfoActivity.class);
                    break;
                case ACTION_EMERGENCY:
                    Log.d("ACTIVITY_EMERGENCY", "Emergency activity initiated.");
                    intent = new Intent(mContext, EmergencyActivity.class);
                    break;
                default:
                    break;
            }

            if(intent != null)
            {
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        }
    }

    private ArrayList<NavDrawerItem> getNavDrawerItems()
    {
        String[] drawerTitles = getResources().getStringArray(R.array.nav_drawer_titles);
        ArrayList<NavDrawerItem> aList = new ArrayList<NavDrawerItem>();
        for(int i = 0; i < drawerTitles.length; i++ )
        {
            NavDrawerItem navItem = new NavDrawerItem(drawerTitles[i],(Drawable) getResources().getDrawable(R.drawable.ic_launcher));
            aList.add(navItem);
        }
        return aList;
    }
}