package andronerds.com.contestapp.navDrawer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
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
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;

import andronerds.com.contestapp.DriveToWinActivity;
import andronerds.com.contestapp.EmergencyActivity;
import andronerds.com.contestapp.InsuranceInfoActivity;
import andronerds.com.contestapp.MainActivity;
import andronerds.com.contestapp.MyTripsActivity;
import andronerds.com.contestapp.MyVehicleActivity;
import andronerds.com.contestapp.obd.OnBoardDiagnostic;
import andronerds.com.contestapp.R;
import andronerds.com.contestapp.SettingsActivity;
import butterknife.InjectView;

/**
 * @author Chris Portokalis
 * @version ContestApp v0.1A
 * @since 2/19/15
 */
public abstract class NavDrawerActivity extends ActionBarActivity
{
    public final String ACTION_HOME = "Home";
    public final String ACTION_STATS = "Stats";
    public final String ACTION_MY_TRIPS = "Trips";
    public final String ACTION_MY_VEHICLE = "Profile";
    public final String ACTION_INSURANCE_INFO = "Insurance Info";
    public final String ACTION_EMERGENCY = "Emergency";
    public final String ACTION_SETTINGS = "Settings";
    public final String ACTION_ACHIEVEMENTS = "Achievements";

    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private CharSequence mTitle;
    private ArrayList<NavDrawerItem> mNavDrawerItems;
    private Context mContext = this;
    private NavDrawerAdapter mNavDrawerAdapter;

    private static int mCurrentSelectionIndex = 1;

    abstract protected Toolbar init();

    @InjectView(R.id.nav_drawer_layout)DrawerLayout mDrawerLayout;
    @InjectView(R.id.left_drawer)ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(OnBoardDiagnostic.isInitialized()){

        }else{
            OnBoardDiagnostic.initialize(this);
            OnBoardDiagnostic.refresh(this);
        }

        /* Init sets content view and
        other specifics based on activity implementation */
        mToolbar = init();

        mTitle = mToolbar.getTitle();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavDrawerItems = getNavDrawerItems();
        mNavDrawerAdapter = new NavDrawerAdapter(mNavDrawerItems, this, mCurrentSelectionIndex);
        mDrawerList.setAdapter(mNavDrawerAdapter);
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

        //mDrawerList.setItemChecked(this.mCurrentSelectionIndex, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    //Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(OnBoardDiagnostic.getDriveMode()){
            getMenuInflater().inflate(R.menu.menu_drive_mode_on, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_drive_mode, menu);
        }
        return super.onPrepareOptionsMenu(menu);
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
            case R.id.drive_mode_button:
                Log.d("MENU", "Drive Mode Pressed");
                if(OnBoardDiagnostic.isActive()){
                    OnBoardDiagnostic.setDriveMode(true);
                    Toast.makeText(getApplicationContext(), "You are now Driving to Win!", Toast.LENGTH_LONG).show();
                    OnBoardDiagnostic.startNewTrip();
                    invalidateOptionsMenu();
                }else{
                    Toast.makeText(getApplicationContext(), "Please connect to your OBD", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.drive_mode_button_on:
                OnBoardDiagnostic.setDriveMode(false);
                Toast.makeText(getApplicationContext(), "Driving mode off", Toast.LENGTH_LONG).show();
                OnBoardDiagnostic.finishTrip();
                invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Log.i("sel", "onClick");
            //drawerItemLayout.setBackgroundColor(getResources().getColor(R.color.background_material_light));
            mDrawerLayout.closeDrawer(mDrawerList);
            mCurrentSelectionIndex = position;
            //mDrawerList.setSelected(true);

            Intent intent = null;
            Log.d("NAV DRAWER ITEM POS", mNavDrawerItems.get(position).getMenuItemName());

            switch(mNavDrawerItems.get(position).getMenuItemName()) {
                case ACTION_HOME:
                    Log.d("ACTIVITY_HOME", "Home activity initiated.");
                    intent = new Intent(mContext, MainActivity.class);
                    break;
                case ACTION_ACHIEVEMENTS:
                    Log.d("ACTIVITY_ACHIEVEMENTS", "Achievements activity initiated.");
                    intent = new Intent(mContext, DriveToWinActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, ACTION_ACHIEVEMENTS);
                    break;
                case ACTION_STATS:
                    intent = new Intent(mContext, DriveToWinActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, ACTION_STATS);
                    break;
                case ACTION_MY_TRIPS:
                    Log.d("ACTIVITY_TRIPS", "My trips activity initiated.");
                    intent = new Intent(mContext, MyTripsActivity.class);
                    break;
                case ACTION_MY_VEHICLE:
                    Log.d("ACTIVITY_VEHICLE", "My vehicle activity initiated.");
                    intent = new Intent(mContext, MyVehicleActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, ACTION_MY_VEHICLE);
                    break;
                case ACTION_INSURANCE_INFO:
                    Log.d("ACTIVITY_INSURANCE", "Insurance info activity initiated.");
                    intent = new Intent(mContext, InsuranceInfoActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, ACTION_INSURANCE_INFO);
                    break;
                case ACTION_EMERGENCY:
                    Log.d("ACTIVITY_EMERGENCY", "Emergency activity initiated.");
                    intent = new Intent(mContext, EmergencyActivity.class);
                    break;
                case ACTION_SETTINGS:
                    Log.d("ACTIVITY_SETTINGS", "Settings activity initiated.");
                    intent = new Intent(mContext, SettingsActivity.class);
                    break;
                default:
                    break;
            }

            if(intent != null)
            {
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                startActivity(intent);
                finish();
            }
        }
    }

    private ArrayList<NavDrawerItem> getNavDrawerItems()
    {
        String[] drawerTitles = getResources().getStringArray(R.array.nav_drawer_titles);
        ArrayList<NavDrawerItem> aList = new ArrayList<NavDrawerItem>();
        int imageResource = 0;
        NavDrawerItem navItem = null;
        for(int i = 0; i < drawerTitles.length; i++ )
        {
            navItem = new NavDrawerItem(drawerTitles[i]);
            imageResource = setImageResource(i, drawerTitles);

            if(imageResource != 0)
            {
                navItem.setMenuIcon(imageResource);
            }
            aList.add(navItem);
        }
        return aList;
    }

    public int setImageResource(int position, String[] drawerTitles)
    {
        int imageResource = 0;
        switch(drawerTitles[position])
        {
            case ACTION_HOME:
                if(mCurrentSelectionIndex == position)
                    imageResource = R.drawable.nav_home_green;
                else
                    imageResource = R.drawable.nav_home_gray;
                break;
            case ACTION_ACHIEVEMENTS:
                if(mCurrentSelectionIndex == position)
                    imageResource = R.drawable.nav_achievements_green;
                else
                    imageResource = R.drawable.nav_achievements_gray;
                break;
            case ACTION_STATS:
                if(mCurrentSelectionIndex == position)
                    imageResource = R.drawable.nav_stats_green;
                else
                    imageResource = R.drawable.nav_stats_gray;
                break;
            case ACTION_MY_TRIPS:
                if(mCurrentSelectionIndex == position)
                    imageResource = R.drawable.nav_trips_green;
                else
                    imageResource = R.drawable.nav_trips_gray;
                break;
            case ACTION_MY_VEHICLE:
                if (mCurrentSelectionIndex == position)
                    imageResource = R.drawable.nav_profile_green;
                else
                    imageResource = R.drawable.nav_profile_gray;
                break;
            case ACTION_INSURANCE_INFO:
                if(mCurrentSelectionIndex == position)
                    imageResource = R.drawable.nav_insurance_green;
                else
                    imageResource = R.drawable.nav_insurance_gray;
                break;
            case ACTION_EMERGENCY:
                if(mCurrentSelectionIndex == position)
                    imageResource = R.drawable.nav_emergency_green;
                else
                    imageResource = R.drawable.nav_emergency_gray;
                break;
            case ACTION_SETTINGS:
                if(mCurrentSelectionIndex == position)
                    imageResource = R.drawable.nav_settings_green;
                else
                    imageResource = R.drawable.nav_settings_gray;
                break;
        }

        return imageResource;
    }

    public void setSelectedDrawerItem(String title)
    {
        Log.d("SELECT DRAWER ITEM", "setSelected... being called " + title);
        for(int i = 0; i < mNavDrawerItems.size(); i++)
        {
            if(mNavDrawerItems.get(i).getMenuItemName().equals(title))
            {
                Log.d("TITLE PAGE", title + " index " + i);
                mCurrentSelectionIndex = i;
                break;
            }
        }
        mNavDrawerItems = getNavDrawerItems();
        mNavDrawerAdapter.setDrawerItems(mNavDrawerItems);
        mNavDrawerAdapter.setCurrentlySelected(mCurrentSelectionIndex);
        mNavDrawerAdapter.notifyDataSetChanged();
    }

    public void setNavDrawerTitle(String title)
    {
        this.mTitle = title;
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            OnBoardDiagnostic.unregReceiver(this);
        }catch(IllegalArgumentException e){

        }
    }

    @Override
    public void onBackPressed()
    {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            OnBoardDiagnostic.unregReceiver(this);
        }catch(IllegalArgumentException e){

        }

        mNavDrawerAdapter.setCurrentlySelected(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        OnBoardDiagnostic.refresh(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            Toast.makeText(getApplicationContext(), "Some Features Require Bluetooth To Work Correctly", Toast.LENGTH_LONG).show();
            OnBoardDiagnostic.setState(false);
        }if(resultCode == RESULT_OK){
            OnBoardDiagnostic.setState(true);
        }
    }

}