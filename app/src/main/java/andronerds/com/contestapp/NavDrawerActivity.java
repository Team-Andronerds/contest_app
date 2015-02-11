package andronerds.com.contestapp;

import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;


public abstract class NavDrawerActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private NavDrawerItem drawerItems;
    private LinearLayout drawerItemLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private CharSequence mTitle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<NavDrawerItem> navDrawerItems = getNavDrawerItems();
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.navdrawerlayout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new NavDrawerAdapter(navDrawerItems,this));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        this.drawerItemLayout = (LinearLayout) findViewById(R.id.navDrawer);


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
