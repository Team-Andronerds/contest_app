package andronerds.com.contestapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Chris on 1/28/2015.
 */
public class NavDrawerAdapter extends BaseAdapter {

    private Context navDrawerContext;
    private ArrayList<NavDrawerItem> menuDrawerItems;



    public NavDrawerAdapter()
    {

    }

    public NavDrawerAdapter(ArrayList<NavDrawerItem> drawerItems, Context context)
    {
        this.menuDrawerItems = drawerItems;
        this.navDrawerContext = context;

    }

    public View getView(int x, View v, ViewGroup vg)
    {

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
