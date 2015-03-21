package andronerds.com.contestapp.fragments.driveToWin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.Trip;
import andronerds.com.contestapp.utils.PictureUtil;
import andronerds.com.contestapp.utils.IdentityStrings;
import andronerds.com.contestapp.views.ProgressWheel;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 3/1/15
 */
public class StatsFragment extends Fragment
{
    // Store instance variables
    private String title;
    private int page;

    private float mCurrentPoints;
    private float mPointsNeeded;
    private int mCurrLevel;
    private int mCurrTotalPoints;

    // newInstance constructor for creating fragment with arguments
    public static StatsFragment newInstance(int page, String title) {
        StatsFragment fragmentFirst = new StatsFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @InjectView(R.id.curr_level)TextView mCurrLevelText;
    @InjectView(R.id.stats_profile_pic)CircleImageView mProfilePic;
    @InjectView(R.id.stats_points)TextView mPointsText;
    @InjectView(R.id.stats_pie_chart)ProgressWheel mPointsChart;
    @InjectView(R.id.stats_text_total_trips)TextView mTotalTrips;
    @InjectView(R.id.stats_text_total_miles) TextView mTotalMiles;
    @InjectView(R.id.stats_text_total_points) TextView mTotalPoints;
    @InjectView(R.id.stats_text_total_harsh_accelerating) TextView mTotalAccel;
    @InjectView(R.id.stats_text_total_harsh_braking)TextView mTotalBrake;
    @InjectView(R.id.stats_text_total_harsh_turns) TextView mTotalTurns;
    @InjectView(R.id.stats_text_total_speeding) TextView mTotalSpeeding;
    @InjectView(R.id.stats_text_total_gas) TextView mTotalGas;
    @InjectView(R.id.stats_text_average_miles)TextView mAvgMiles;
    @InjectView(R.id.stats_text_average_points)TextView mAvgPoints;
    @InjectView(R.id.stats_average_harsh_accelerating)TextView mAvgAccel;
    @InjectView(R.id.stats_text_average_harsh_braking)TextView mAvgBrake;
    @InjectView(R.id.stats_text_average_harsh_turns)TextView mAvgTurns;
    @InjectView(R.id.stats_text_average_speeding)TextView mAvgSpeeding;
    @InjectView(R.id.stats_text_average_gas)TextView mAvgGas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        ButterKnife.inject(this, view);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
        boolean usingGooglePlus = sharedPreferences.getBoolean(IdentityStrings.USER_IS_GOOGLE_PLUS, false);
        String userName = sharedPreferences.getString(IdentityStrings.USER_NAME, "");

        mCurrTotalPoints = getTotalPoints(userName);
        mCurrLevel = calcLevel(mCurrTotalPoints);
        mPointsNeeded = mCurrLevel*100;
        mCurrentPoints = calcCurrPoints();
        mPointsText.setText(""+mCurrentPoints+" / " + mPointsNeeded);
        mCurrLevelText.setText("Level " + mCurrLevel);

        float percent = (100 * mCurrentPoints) / mPointsNeeded;

        Log.d("PERCENT", String.format("%.0f%%", percent));

        mPointsChart.setText(String.format("%.0f%%", percent));

        int circlePercent = Math.round((360 * mCurrentPoints) / mPointsNeeded);

        mPointsChart.setProgress(circlePercent);

        int profilePicInt = 0;
        String profilePicString = "";

        try {
            Log.d("STATS FRAGMENT", sharedPreferences.getString(IdentityStrings.USER_PROFILE_PIC, ""));
            profilePicInt = Integer.parseInt(sharedPreferences.getString(IdentityStrings.USER_PROFILE_PIC, ""));
        } catch (NumberFormatException e) {
            System.out.println("Wrong number");
            profilePicString = sharedPreferences.getString(IdentityStrings.USER_PROFILE_PIC, "");
        }

        if(profilePicInt != 0)
        {
            Picasso.with(this.getActivity())
                    .load(profilePicInt)
                    .fit()
                    .into(mProfilePic);
        }
        else if(!profilePicString.equals(""))
        {
            if(usingGooglePlus) {
                Picasso.with(this.getActivity())
                        .load(profilePicString)
                        .fit()
                        .into(mProfilePic);
            }
            else
            {
                mProfilePic.setImageBitmap(PictureUtil.loadImageFromStorage(profilePicString));
            }
        }
        else
        {
            Picasso.with(this.getActivity())
                    .load(R.drawable.ic_profile_null)
                    .fit()
                    .into(mProfilePic);
        }

        mProfilePic.setBorderWidth(3);
        mProfilePic.setBorderColor(getActivity().getResources().getColor(R.color.black));

        setupStats(userName);

        return view;
    }


    public void setupStats(String userName)
    {
        List<Trip> userTrips = Trip.find(Trip.class, "name = ?", "loll");

        int tripsCount = userTrips.size();

        if(tripsCount != 0)
        {
            Log.d("STATS TRIPS","TRIPS AVAILABLE");

            int miles = 0;
            int points = 0;
            int brakes = 0;
            int turns = 0;
            int gas = 0;
            int speeding = 0;
            int accel = 0;

            for(Trip trip : userTrips)
            {
                points += trip.getPoints();
                miles += trip.getTripMileage();
                brakes += trip.getHarshBrakeCount();
                turns += trip.getHarshTurnCount();
                gas += trip.getLowGasCount();
                speeding += trip.getSpeedingCount();
                accel += trip.getHarshAccelCount();
            }

            float avgPoints = (float)points/(float)tripsCount;
            float avgMiles = (float)miles/(float)tripsCount;
            float avgBrakes = (float)brakes/(float)tripsCount;
            float avgTurns = (float)turns/(float)tripsCount;
            float avgGas = (float)gas/(float)tripsCount;
            float avgSpeeding = (float)speeding/(float)tripsCount;
            float avgAccel = (float)accel/(float)tripsCount;

            mTotalTrips.setText(""+tripsCount);
            mTotalMiles.setText(""+miles);
            mTotalPoints.setText(""+points);
            mTotalBrake.setText(""+brakes);
            mTotalTurns.setText(""+turns);
            mTotalGas.setText(""+gas);
            mTotalSpeeding.setText(""+speeding);
            mTotalAccel.setText(""+accel);

            DecimalFormat df = new DecimalFormat("0.0");

            mAvgMiles.setText(df.format(avgMiles));
            mAvgPoints.setText(df.format(avgPoints));
            mAvgBrake.setText(df.format(avgBrakes));
            mAvgTurns.setText(df.format(avgTurns));
            mAvgGas.setText(df.format(avgGas));
            mAvgSpeeding.setText(df.format(avgSpeeding));
            mAvgAccel.setText(df.format(avgAccel));
        }
        else
        {
            Log.d("STATS TRIPS","NO TRIPS AVAILABLE");
        }

    }

    public int getTotalPoints(String userName)
    {
        List<Trip> trips = Trip.find(Trip.class,"name = ?","loll");


        if(trips.size() != 0)
        {
            int total = 0;
            for(Trip trip : trips)
            {
                total += trip.getPoints();
            }
            return total;
        }
        else
        {
            return 0;
        }


    }

    public int calcLevel(int points)
    {
        if(points >= 100) {
            int level = 0;
            for(int i = 0; points >= 0; i++)
            {
                points -= 100 * i;
                level = i;
            }

            Log.d("MATH TEST", Integer.toString(level));

            return level;
        }
        else
        {
            return 1;
        }
    }


    public int calcCurrPoints()
    {
        if(mCurrLevel > 1) {
            int points = 0;
            for(int i = mCurrLevel-1; i > 0; i--)
            {
                points += i * 100;
            }
            Log.d("MATH CURR LEVEL PROG",Integer.toString(points));
            return mCurrTotalPoints - points;
        }
        else
        {
            return mCurrTotalPoints;
        }
    }
}
