package andronerds.com.contestapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import andronerds.com.contestapp.fragments.driveToWin.AchievementsFragment;
import andronerds.com.contestapp.fragments.driveToWin.LeaderboardsFragment;
import andronerds.com.contestapp.fragments.driveToWin.MilestonesFragment;
import andronerds.com.contestapp.fragments.driveToWin.StatsFragment;
import andronerds.com.contestapp.navDrawer.NavDrawerActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v0.1A
 * @since 2/19/15
 */
public class DriveToWinActivity extends NavDrawerActivity
{
    private CharSequence mTitle = "Drive To Win";

    @InjectView(R.id.drive_to_win_toolbar)Toolbar mToolbar;
    @InjectView(R.id.drive_view_pager)ViewPager mDrivePager;

    @Override
    protected Toolbar init()
    {
        setContentView(R.layout.activity_drive_to_win);
        ButterKnife.inject(this);

        /*
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DriveToWinFragment driveFragment = new DriveToWinFragment();
        fragmentTransaction.add(R.id.drive_to_win_fragment_container, driveFragment);
        fragmentTransaction.commit();
        */

        DrivePagerAdapter adapterViewPager = new DrivePagerAdapter(getSupportFragmentManager());
        mDrivePager.setAdapter(adapterViewPager);

        mToolbar.showOverflowMenu();
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);

        return mToolbar;
    }

    public class DrivePagerAdapter extends FragmentPagerAdapter
    {
        public DrivePagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return StatsFragment.newInstance(0, "Stats");
                case 1:
                    return LeaderboardsFragment.newInstance(1, "Leaderboards");
                case 2:
                    return AchievementsFragment.newInstance(2, "Achievements");
                case 3:
                    return MilestonesFragment.newInstance(3, "Milestones");
            }
            return null;
        }

        @Override
        public int getCount()
        {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position)
            {
                case 0:
                    return "Stats";
                case 1:
                    return "Leaderboards";
                case 2:
                    return "Achievements";
                case 3:
                    return "Milestones";
            }
            return null;
        }
    }
}
