package andronerds.com.contestapp.fragments.driveToWin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andronerds.com.contestapp.R;
import butterknife.ButterKnife;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 3/1/15
 */
public class LeaderboardsFragment extends Fragment
{
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static LeaderboardsFragment newInstance(int page, String title) {
        LeaderboardsFragment leaderboardsFragment = new LeaderboardsFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        leaderboardsFragment.setArguments(args);
        return leaderboardsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_leaderboards, container, false);
        ButterKnife.inject(this, view);
        return view;
    }
}
