package andronerds.com.contestapp.navDrawer;

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

import andronerds.com.contestapp.R;
import butterknife.InjectView;

/**
 * @author Chris Portokalis
 * @version ContestApp v0.1A
 * @since 2/19/15
 */
public abstract class NavDrawerActivity extends ActionBarActivity
{
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private CharSequence mTitle;

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

        ArrayList<NavDrawerItem> navDrawerItems = getNavDrawerItems();
        mDrawerList.setAdapter(new NavDrawerAdapter(navDrawerItems, this));
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
                getSupportActionBar().setTitle("Drawer");
                supportInvalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintColor(this.getResources().getColor(R.color.toolbar_color));
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Log.i("sel", "onClick");
            //drawerItemLayout.setBackgroundColor(getResources().getColor(R.color.background_material_light));
            mDrawerLayout.closeDrawer(mDrawerList);
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