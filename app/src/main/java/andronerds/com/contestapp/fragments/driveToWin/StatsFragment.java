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

import andronerds.com.contestapp.R;
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

    // newInstance constructor for creating fragment with arguments
    public static StatsFragment newInstance(int page, String title) {
        StatsFragment fragmentFirst = new StatsFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @InjectView(R.id.stats_profile_pic)CircleImageView mProfilePic;
    @InjectView(R.id.stats_points)TextView mPointsText;
    @InjectView(R.id.stats_pie_chart)ProgressWheel mPointsChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        ButterKnife.inject(this, view);

        mCurrentPoints = 40;
        mPointsNeeded = 100;
        float percent = (100 * mCurrentPoints) / mPointsNeeded;

        Log.d("PERCENT", String.format("%.0f%%", percent));

        mPointsChart.setText(String.format("%.0f%%", percent));

        int circlePercent = Math.round((360 * mCurrentPoints) / mPointsNeeded);

        mPointsChart.setProgress(circlePercent);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
        Picasso.with(this.getActivity())
                .load(sharedPreferences.getString(IdentityStrings.USER_PROFILE_PIC, ""))
                .fit()
                .into(mProfilePic);

        mProfilePic.setBorderWidth(3);
        mProfilePic.setBorderColor(getActivity().getResources().getColor(R.color.black));

        return view;
    }
}
