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
public class MilestonesFragment extends Fragment
{
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static MilestonesFragment newInstance(int page, String title) {
        MilestonesFragment milestonesFragment = new MilestonesFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        milestonesFragment.setArguments(args);
        return milestonesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_milestones, container, false);
        ButterKnife.inject(this, view);

        return view;
    }
}
