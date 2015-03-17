package andronerds.com.contestapp.data;

import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by Chris on 3/8/2015.
 */
public class Achievements extends User {

    private String title;
    private String description;
    private boolean hasAchieved;
    private int achievementImage;


    public Achievements()
    {

    }

    public Achievements(String setTitle, String setDescription, boolean setAchieved, int setImage, String name)
    {
        this.title = setTitle;
        this.description = setDescription;
        this.hasAchieved = setAchieved;
        this.achievementImage = setImage;
        this.setName(name);
        Log.d("ID check sugar->achievements","ID = " + setImage);
    }

    public String getTitle()
    {
        return this.title;
    }

    //Getters
    public String getDescription()
    {
        return this.description;
    }

    public Boolean didAchieve()
    {
        return this.hasAchieved;
    }

    public int getAchievementImage()
    {
        return this.achievementImage;
    }

    //setters

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setHasAchieved(Boolean achieved)
    {
        this.hasAchieved = achieved;
    }

    public void setAchievementImage(int image)
    {
        this.achievementImage = image;
    }


}
