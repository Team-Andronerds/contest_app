package andronerds.com.contestapp.navDrawer;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.utils.IdentityStrings;

/**
 * Created by Chris on 1/28/2015.
 */
public class NavDrawerAdapter extends BaseAdapter {

    private Context navDrawerContext;
    private ArrayList<NavDrawerItem> menuDrawerItems;
    private SharedPreferences userProfilePrefs;
    private String[] menuDrawerItemTypes;
    private int mCurrentlySelected;

    public NavDrawerAdapter() {}

    public NavDrawerAdapter(ArrayList<NavDrawerItem> drawerItems, Context context, int selected)
    {
        this.menuDrawerItems = drawerItems;
        this.navDrawerContext = context;
        this.menuDrawerItemTypes = navDrawerContext.getResources().getStringArray(R.array.nav_drawer_types);
        this.mCurrentlySelected = selected;

    }

    public View getView(int x, View v, ViewGroup vg)
    {
        LayoutInflater inflater = (LayoutInflater) this.navDrawerContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(x == 0)
        {
            v = inflater.inflate(R.layout.header, null, false);
            TextView name = (TextView) v.findViewById(R.id.name);
            ImageView profilePic = (ImageView) v.findViewById(R.id.profile_pic);
            userProfilePrefs = navDrawerContext.getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);

            name.setText(userProfilePrefs.getString(IdentityStrings.USER_NAME, "Name"));
            Picasso.with(navDrawerContext)
                    .load(userProfilePrefs.getString(IdentityStrings.USER_PROFILE_PIC, ""))
                    .fit()
                    .into(profilePic);
            //profilePic.setImageDrawable(v.getResources().getDrawable(R.drawable.me));
        }
        else
        {
            if(this.menuDrawerItemTypes[x].equals("Activity"))
            {
                v = inflater.inflate(R.layout.drawer_list_item, null, false);
                TextView title = (TextView) v.findViewById(R.id.nav_drawer_text);
                ImageView icon = (ImageView) v.findViewById(R.id.drawer_icon);
                if(x == mCurrentlySelected)
                {
                    title.setTextColor(navDrawerContext.getResources().getColor(R.color.item_selected_text_color));
                    v.setBackgroundColor(navDrawerContext.getResources().getColor(R.color.item_selected_color));
                }
                title.setText(this.menuDrawerItems.get(x).getMenuItemName());
                Picasso.with(v.getContext())
                        .load(menuDrawerItems.get(x).getMenuIcon())
                        .fit()
                        .into(icon);
            }
            else if(this.menuDrawerItemTypes[x].equals("Header"))
            {
                v = inflater.inflate(R.layout.list_header, null, false);
                TextView title = (TextView) v.findViewById(R.id.header_text);
                title.setText(this.menuDrawerItems.get(x).getMenuItemName());
                v.setClickable(false);
                v.setOnClickListener(null);
            }
            else
            {
                v = inflater.inflate(R.layout.drawer_list_item, null, false);
                TextView title = (TextView) v.findViewById(R.id.nav_drawer_text);
                ImageView icon = (ImageView) v.findViewById(R.id.drawer_icon);
                if(x == mCurrentlySelected)
                {
                    title.setTextColor(navDrawerContext.getResources().getColor(R.color.item_selected_text_color));
                    v.setBackgroundColor(navDrawerContext.getResources().getColor(R.color.item_selected_color));
                }
                title.setText(this.menuDrawerItems.get(x).getMenuItemName());
                Picasso.with(v.getContext())
                        .load(menuDrawerItems.get(x).getMenuIcon())
                        .fit()
                        .into(icon);
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
