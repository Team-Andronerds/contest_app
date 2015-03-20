package andronerds.com.contestapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import andronerds.com.contestapp.data.Achievements;
import andronerds.com.contestapp.fragments.HomeFragment;
import andronerds.com.contestapp.navDrawer.NavDrawerActivity;
import andronerds.com.contestapp.data.Achievements;
import andronerds.com.contestapp.utils.IdentityStrings;
import butterknife.ButterKnife;
import butterknife.InjectView;




public class MainActivity extends NavDrawerActivity
{
    private CharSequence mTitle = "Home";
    private List<Achievements> achList;

    @InjectView(R.id.main_activity_toolbar)Toolbar mToolbar;

    @Override
    protected Toolbar init()
    {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //init achievements
        this.initAchievements();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.home_fragment_container, homeFragment);
        fragmentTransaction.commit();

        //Toolbar settings
        mToolbar.showOverflowMenu();
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);

        /*
            Testing sugar for achievements
         */



        Log.d("NAV_DRAWER", "Activating nav drawer activity");
        return mToolbar;
    }

    private void initAchievements()
    {
        //Achievements.deleteAll(Achievements.class);

        SharedPreferences userProfilePrefs = getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
        List<Achievements> achList = Achievements.find(Achievements.class, "name = ?", userProfilePrefs.getString(IdentityStrings.USER_NAME, "Name"));
        List<String> achTitles =  Arrays.asList(getResources().getStringArray(R.array.achievement_title));

        if(achList.size() == 0)
        {
            String[] achDesc = getResources().getStringArray(R.array.achievement_description);
            String[] earned = getResources().getStringArray(R.array.achievement_have_earned);
            Achievements ach;

            Log.d("ID CHECK MAIN","ID = " + R.drawable.ic_profile_null);

            for(int i = 0; i < achTitles.size(); i++)
            {
                ach = new Achievements(achTitles.get(i),achDesc[i],earned[i].equals("true"),R.drawable.nav_vehicle_gray,userProfilePrefs.getString(IdentityStrings.USER_NAME, "Name"));
                ach.save();
            }
        }
        else if(achTitles.size() != achList.size())
        {
            Achievements.deleteAll(Achievements.class);
            String[] achDesc = getResources().getStringArray(R.array.achievement_description);
            String[] earned = getResources().getStringArray(R.array.achievement_have_earned);
            Achievements ach;

            Log.d("ID CHECK MAIN","ID = " + R.drawable.ic_profile_null);

            for(int i = 0; i < achTitles.size(); i++)
            {
                ach = new Achievements(achTitles.get(i),achDesc[i],earned[i].equals("true"),R.drawable.nav_vehicle_gray,userProfilePrefs.getString(IdentityStrings.USER_NAME, "Name"));
                ach.save();
            }
        }
    }
}
