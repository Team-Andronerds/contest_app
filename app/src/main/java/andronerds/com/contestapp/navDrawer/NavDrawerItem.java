package andronerds.com.contestapp.navDrawer;

/**
 * Created by Chris on 1/28/2015.
 */
public class NavDrawerItem {

    private String menuItemName;
    private int menuIcon;

    public NavDrawerItem(String title, int draw)
    {
        this.menuItemName = title;
        this.menuIcon = draw;
    }

    public NavDrawerItem(String title)
    {
        this.menuItemName = title;
    }

    /*
     *  Getters for MenuDrawerItem class
     */

    public String getMenuItemName()
    {
        return this.menuItemName;
    }

    public int getMenuIcon()
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

    public void setMenuIcon(int newIcon)
    {
        this.menuIcon = newIcon;
    }
}
