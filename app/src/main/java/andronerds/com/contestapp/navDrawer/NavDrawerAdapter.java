package andronerds.com.contestapp.navDrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import andronerds.com.contestapp.R;

/**
 * Created by Chris on 1/28/2015.
 */
public class NavDrawerAdapter extends BaseAdapter {

    private Context navDrawerContext;
    private ArrayList<NavDrawerItem> menuDrawerItems;
    private String[] menuDrawerItemTypes;

    public NavDrawerAdapter() {}

    public NavDrawerAdapter(ArrayList<NavDrawerItem> drawerItems, Context context)
    {
        this.menuDrawerItems = drawerItems;
        this.navDrawerContext = context;
        this.menuDrawerItemTypes = navDrawerContext.getResources().getStringArray(R.array.nav_drawer_types);
    }

    public View getView(int x, View v, ViewGroup vg)
    {
        LayoutInflater inflater = (LayoutInflater) this.navDrawerContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(x == 0)
        {
            v = inflater.inflate(R.layout.header, null, false);
            TextView name = (TextView) v.findViewById(R.id.name);
            ImageView profilePic = (ImageView) v.findViewById(R.id.profile_pic);
            name.setText("Chris Portokalis");
            profilePic.setImageDrawable(v.getResources().getDrawable(R.drawable.me));

        }
        else
        {
            if(this.menuDrawerItemTypes[x].equals("Activity"))
            {
                v = inflater.inflate(R.layout.drawer_list_item, null, false);
                TextView title = (TextView) v.findViewById(R.id.nav_drawer_text);
                ImageView icon = (ImageView) v.findViewById(R.id.drawer_icon);
                title.setText(this.menuDrawerItems.get(x).getMenuItemName());
                icon.setImageDrawable(this.menuDrawerItems.get(x).getMenuIcon());
            }
            else if(this.menuDrawerItemTypes[x].equals("Header"))
            {
                v = inflater.inflate(R.layout.list_header, null, false);
                TextView title = (TextView) v.findViewById(R.id.header_text);
                title.setText(this.menuDrawerItems.get(x).getMenuItemName());
            }
            else
            {
                v = inflater.inflate(R.layout.drawer_list_item, null, false);
                TextView title = (TextView) v.findViewById(R.id.nav_drawer_text);
                ImageView icon = (ImageView) v.findViewById(R.id.drawer_icon);
                title.setText(this.menuDrawerItems.get(x).getMenuItemName());
                icon.setImageDrawable(this.menuDrawerItems.get(x).getMenuIcon());

            }
        }

        return v;
    }

    public Object getItem(int offset)
    {
        return this.menuDrawerItems.get(offset);
    }

    public long getItemId(int pos)
    {
        return pos;
    }

    public int getCount()
    {
        return this.menuDrawerItems.size();
    }
}
