package andronerds.com.contestapp;

import android.content.Intent;
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
public class DriveToWinActivity extends NavDrawerActivity implements ViewPager.OnPageChangeListener
{
    private final int POSITION_STATS = 0;
    private final int POSITION_LEADERBOARDS = 1;
    private final int POSITION_ACHIEVEMENTS = 2;
    private final int POSITION_MILESTONES = 3;

    private CharSequence mTitle = "Drive To Win";
    private DrivePagerAdapter adapterViewPager;
    private int mInitialPosition;

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

       String startFrag = getIntent().getExtras().getString(Intent.EXTRA_TEXT);
        if(startFrag != null)
        {
            switch(startFrag)
            {
                case ACTION_STATS:
                    mInitialPosition = POSITION_STATS;
                    break;
                case ACTION_LEADERBOARDS:
                    mInitialPosition = POSITION_LEADERBOARDS;
                    break;
                case ACTION_ACHIEVEMENTS:
                    mInitialPosition = POSITION_ACHIEVEMENTS;
                    break;
                case ACTION_MILESTONES:
                    mInitialPosition = POSITION_MILESTONES;
                    break;
                default:
                    mInitialPosition = POSITION_STATS;
                    break;
            }
        }

        adapterViewPager = new DrivePagerAdapter(getSupportFragmentManager());
        mDrivePager.setAdapter(adapterViewPager);
        mDrivePager.setOnPageChangeListener(this);
        mDrivePager.setCurrentItem(mInitialPosition);

        mToolbar.showOverflowMenu();
        mToolbar.setTitle(adapterViewPager.getPageTitle(mDrivePager.getCurrentItem()));
        setSupportActionBar(mToolbar);

        return mToolbar;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
        mToolbar.setTitle(adapterViewPager.getPageTitle(position));
        setSelectedDrawerItem(adapterViewPager.getPageTitle(position).toString());
        setNavDrawerTitle(adapterViewPager.getPageTitle(position).toString());
    }

    @Override
    public void onPageSelected(int position)
    {

    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

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
                case POSITION_STATS:
                    return StatsFragment.newInstance(POSITION_STATS, ACTION_STATS);
                case POSITION_LEADERBOARDS:
                    return LeaderboardsFragment.newInstance(POSITION_LEADERBOARDS, ACTION_LEADERBOARDS);
                case POSITION_ACHIEVEMENTS:
                    return AchievementsFragment.newInstance(POSITION_ACHIEVEMENTS, ACTION_ACHIEVEMENTS);
                case POSITION_MILESTONES:
                    return MilestonesFragment.newInstance(POSITION_MILESTONES, ACTION_MILESTONES);
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
                case POSITION_STATS:
                    return ACTION_STATS;
                case POSITION_LEADERBOARDS:
                    return ACTION_LEADERBOARDS;
                case POSITION_ACHIEVEMENTS:
                    return ACTION_ACHIEVEMENTS;
                case POSITION_MILESTONES:
                    return ACTION_MILESTONES;
            }
            return null;
        }
    }
}
