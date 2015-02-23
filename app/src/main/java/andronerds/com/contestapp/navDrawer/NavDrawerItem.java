package andronerds.com.contestapp.navDrawer;

import android.graphics.drawable.Drawable;

/**
 * Created by Chris on 1/28/2015.
 */
public class NavDrawerItem {

    private String menuItemName;
    private Drawable menuIcon;

    public NavDrawerItem(String title, Drawable draw)
    {
        this.menuItemName = title;
        this.menuIcon = draw;
    }

    /*
     *  Getters for MenuDrawerItem class
     */

    public String getMenuItemName()
    {
        return this.menuItemName;
    }

    public Drawable getMenuIcon()
    {
        return this.menuIcon;
    }


    /*
     * Setters for MenuDrawerItem class
     */
    public void setMenuItemName(String newName)
    {
        this.menuItemName = newName;
    }

    public void setMenuIcon(Drawable newIcon)
    {
        this.menuIcon = newIcon;
    }
}
